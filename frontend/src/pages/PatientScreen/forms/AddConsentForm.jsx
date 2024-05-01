import { useState } from "react";
import { MdAdd } from "react-icons/md";
import Select from "react-select";
import {
  Button,
  Dialog,
  DialogHeader,
  DialogBody,
  DialogFooter,
} from "@material-tailwind/react";
import { toast } from "react-toastify";

import useAuth from "../../../hooks/useAuth";
import { jwtDecode } from "jwt-decode";
import { useForm } from "react-hook-form";
import useFetchEventSource from "../../../hooks/useFetchEventSource";

function AddConsentForm({ patientDetails, sendDataToParent }) {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(!open);
  const options = [
    { value: "OPConsultation", label: "OP consultation" },
    { value: "DischargeSummary", label: "Discharge Summary" },
    { value: "ImmunizationRecord", label: "Immunization Record" },
    { value: "WellnessRecord", label: "Wellness Record" },
    { value: "DiagnosticReport", label: "Diagnostic Reports" },
    { value: "Prescription", label: "Prescription" },
    { value: "HealthDocumentRecord", label: "Health Document Record" },
  ];

  const customStyles = {
    multiValue: (base) => ({
      ...base,
      overflow: "hidden",
      whiteSpace: "nowrap",
      textOverflow: "ellipsis",
    }),
    valueContainer: (base) => ({
      ...base,
      display: "flex",
      flexWrap: "nowrap",
      overflowX: "auto", // Allows horizontal scrolling
    }),
    // Apply similar styles to options if needed
  };

  const { auth } = useAuth();
  const doctorDecoded = auth?.accessToken
    ? jwtDecode(auth.accessToken)
    : undefined;
  const eventSource = useFetchEventSource();

  const {
    register,
    handleSubmit,
    reset,
    getValues,
    formState: { errors },
  } = useForm();

  const [hiTypes, setHiTypes] = useState([]);
  const handleChange = (selectedOptions) => {
    const selectedHiTypes = selectedOptions.map((option) => option.value);
    setHiTypes(selectedHiTypes);
  };

  const onSubmit = async () => {
    const data = getValues();
    const formattedData = {
      ...data,
      permission_from: new Date(data.permission_from).toISOString(),
      permission_to: new Date(data.permission_to).toISOString(),
      data_erase_at: new Date(data.data_erase_at).toISOString(),
      hi_type: hiTypes,
      identifier_value: doctorDecoded.registrationNumber,
      requester_name: "Dr. " + doctorDecoded.name,
      patient_id: patientDetails.mrn,
      patient_id_sbx: patientDetails.abhaAddress,
    };

    try {
      const abortController = new AbortController();
      await eventSource("/consent/consentInit", {
        method: "POST",
        body: JSON.stringify(formattedData),
        onmessage(response) {
          var status = JSON.parse(response.data).statusCodeValue;
          if (status >= 400) toast.error(response.data)
          else if (status === 200) {
            sendDataToParent(true);
            toast.success(JSON.parse(response.data).body);
            setOpen(!open);
          }
          abortController.abort();
        },
        onclose(resp) {
          // console.log(resp);
          abortController.abort();
        },
        onerror(error) {
          // console.log(error);
          abortController.abort();
          throw new Error(error);
        },
        signal: abortController.signal,
      });
    } catch (err) {
      // console.log(err);
      //   toast.error(err);
    }
  };
  return (
    <div>
      <button
        onClick={handleOpen}
        className="inline-flex gap-[15px] px-[1.05rem] m-2 py-[0.25rem] h-[2.8rem] justify-center items-center text-white w-fit hover:bg-[#276059] bg-[#006666] rounded-[10px]"
      >
        <MdAdd className="h-[35px] w-[35px] m-auto" />
        <div className="relative w-fit font-semibold m-auto text-[20px]">
          Add Consent
        </div>
      </button>

      <Dialog open={open} onClose={handleOpen} size="lg">
        <DialogHeader>Consent Request Form</DialogHeader>
        <div className="h-[1px] bg-[#827F7F82]"></div>
        <DialogBody>
          <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl  p-5">
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">Purpose of Request</p>
              <select
                className="w-full rounded-md font-semibold"
                {...register("purpose_code", { required: true })}
              >
                <option hidden defaultValue={true}>
                  Select Purpose
                </option>
                <option className="font-medium" value="CAREMGT">
                  Care Management
                </option>
                <option className="font-medium" value="BTG">
                  Break the Glass
                </option>
                <option className="font-medium" value="PUBHLTH">
                  Public Health
                </option>
                <option className="font-medium" value="DSRCH">
                  Disease Specific Healthcare Research
                </option>
                <option className="font-medium" value="PatRQT">
                  Self Requested
                </option>
              </select>
            </div>
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">Requested by</p>
              <p className="m-2 text-[#292524]">Dr. {doctorDecoded.name}</p>
            </div>
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">
                Requester Medical Number
              </p>
              <p className="m-2 text-[#292524]">
                {doctorDecoded.registrationNumber}
              </p>
            </div>
            <div className="flex flex-col"></div>
          </div>
          <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl p-5">
            <div>
              <div className="flex flex-col">
                <p className="text-xl pb-2 font-medium">Health info from</p>
                <input
                  className="rounded-md"
                  type="datetime-local"
                  {...register("permission_from", { required: true })}
                />
              </div>
            </div>
            <div>
              <div className="flex flex-col">
                <p className="text-xl pb-2 font-medium">Health info to</p>
                <input
                  className="rounded-md"
                  type="datetime-local"
                  {...register("permission_to", { required: true })}
                />
              </div>
            </div>
            <div>
              <div className="flex flex-col">
                <p className="text-xl pb-2">Health info type</p>
                <Select
                  className="rounded-md text-ellipsis"
                  classNamePrefix="react-select"
                  options={options}
                  isMulti
                  closeMenuOnSelect={false}
                  hideSelectedOptions={true}
                  onChange={handleChange}
                  styles={customStyles}
                />
              </div>
            </div>
          </div>
          <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl  p-5">
            <div>
              <div className="flex flex-col">
                <p className="text-xl pb-2 font-medium">Consent Expiry</p>
                <input
                  className="rounded-md w-full"
                  type="datetime-local"
                  {...register("data_erase_at", { required: true })}
                />
              </div>
            </div>
            <div>
              <div className="flex flex-col"></div>
            </div>
          </div>
          <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
        </DialogBody>
        <DialogFooter>
          <Button
            variant="text"
            color="red"
            onClick={handleOpen}
            className="mr-1"
          >
            <span>Cancel</span>
          </Button>

          <Button variant="filled" className="bg-[#FFA000]" onClick={onSubmit}>
            <span>Request Consent</span>
          </Button>
        </DialogFooter>
      </Dialog>
    </div>
  );
}

export default AddConsentForm;
