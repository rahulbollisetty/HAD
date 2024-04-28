import {
  Button,
  Dialog,
  DialogHeader,
  DialogBody,
  DialogFooter,
} from "@material-tailwind/react";
import { useEffect, useState } from "react";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

const DoctorDetail = (patient) => {
  const [role, setRole] = useState("");
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(!open);
  const axiosPrivate = useAxiosPrivate();

  const deletePatient = async (patientId) => {
    try {
      const requestBody = {
        patientId: parseInt(patientId),
      };
      const response = await axiosPrivate.post(
        `http://127.0.0.1:9005/patient/deletePatient`,
        requestBody
      );
      console.log(response.data);
    } catch (err) {
      console.log(err);
      if (!err?.response) {
        console.error("No Server Response");
      }
    }
  };

  return (
    <div>
      <button
        onClick={handleOpen}
        className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                            border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
      >
        {/* <MdAdd className="h-[35px] w-[35px] m-auto" /> */}
        <div className="relative w-fit font-semibold m-auto text-[20px]">
          Delete Patient
        </div>
      </button>

      <Dialog open={open} onClose={handleOpen} size="xl">
        <DialogHeader>Doctor Details</DialogHeader>
        <div className="h-[1px] bg-[#827F7F82]"></div>

        <DialogBody>
          This are very health information think twice before deleting the
          records!!
          <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
        </DialogBody>
        <DialogFooter>
          {role === "head_doctor" && (
            <Button
              variant="filled"
              className="bg-[#FFA000]"
              onClick={() => deletePatient(patient)}
            >
              <span>Delete Patient</span>
            </Button>
          )}
          <Button
            variant="filled"
            className="bg-[#FFA000]"
            onClick={() => deletePatient(patient.patient.mrn)}
          >
            <span>Delete Patient</span>
          </Button>

          <Button
            variant="text"
            color="red"
            onClick={handleOpen}
            className="mr-1"
          >
            <span>Cancel</span>
          </Button>
        </DialogFooter>
      </Dialog>
    </div>
  );
};
export default DoctorDetail;
