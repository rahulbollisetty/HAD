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

function AddConsentForm() {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(!open);
  const options = [
    { value: "OP consultation", label: "OP consultation" },
    { value: "Discharge Summary", label: "Discharge Summary" },
    { value: "Immunization Record", label: "Immunization Record" },
    { value: "Wellness Record", label: "Wellness Record" },
    { value: "Diagnostic Reports", label: "Diagnostic Reports" },
    { value: "Prescription", label: "Prescription" },
    { value: "Health Document Record", label: "Health Document Record" },
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

  const handleChange = (selectedOptions) => {
    console.log(selectedOptions);
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
          <div className="grid grid-cols-2  gap-5 text-[#7B7878] font-medium text-xl p-5">
            <div>
              <div className="flex flex-col">
                <p className="text-xl pb-2 font-medium">ABHA ID</p>
                <div className=" ">
                  <input className="rounded-md"></input>
                  <Button className="bg-gray-600 rounded-none">
                    <span>Verify</span>
                  </Button>
                </div>
              </div>
            </div>
            <div>
              <div className="flex flex-col">
                <p className="text-xl pb-2 font-medium">Verify OTP</p>
              </div>
            </div>
          </div>
          <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl  p-5">
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">Purpose of Request</p>
              <select className="w-full rounded-md">
                <option></option>
              </select>
            </div>
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">Requested by</p>
              <select className="w-full rounded-md">
                <option></option>
              </select>
            </div>
            <div className="flex flex-col"></div>
          </div>
          <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl p-5">
            <div>
              <div className="flex flex-col">
                <p className="text-xl pb-2 font-medium">Health info from</p>
                <input className="rounded-md" type="date" />
              </div>
            </div>
            <div>
              <div className="flex flex-col">
                <p className="text-xl pb-2 font-medium">Health info to</p>
                <input className="rounded-md" type="date" />
              </div>
            </div>
            <div>
              <div className="flex flex-col">
                <p className="text-xl pb-2">Health info type</p>
                {/* <select
                  className="w-full rounded-md"
                  multiple={true}
                  value={info}
                >
                  <option value="OP consultation">OP consultation</option>
                  <option value="Discharge Summary">Discharge Summary</option>
                  <option value="Immunization Record">
                    Immunization Record
                  </option>
                  <option value="Wellness Record">Wellness Record</option>
                  <option value="Diagnostic Reports">Diagnostic Reports</option>
                  <option value="Prescription">Prescription</option>
                  <option value="Health Document Record">
                    Health Document Record
                  </option>
                </select> */}
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
                <input className="rounded-md w-full" type="date" />
              </div>
            </div>
            <div>
              <div className="flex flex-col">
                <p className="text-xl pb-2 font-medium">Time</p>
                <input className="rounded-md w-full" type="time" />
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

          <Button variant="filled" className="bg-[#FFA000]">
            <span>Request Consent</span>
          </Button>
        </DialogFooter>
      </Dialog>
    </div>
  );
}

export default AddConsentForm;
