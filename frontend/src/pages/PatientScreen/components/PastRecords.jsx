import { MdSearch, MdAdd } from "react-icons/md";
import { FaCaretRight } from "react-icons/fa";
import React from "react";
import {
  Button,
  Dialog,
  DialogHeader,
  DialogBody,
  DialogFooter,
} from "@material-tailwind/react";
import AddAppointmentForm from "../forms/AddAppointmentForm";

function PastRecords() {

  const [open, setOpen] = React.useState(false);

  const handleOpen = () => setOpen(!open);

  return (
    <div className="border mx-3 my-4 border-[#006666] rounded-md border-l-4">
      <div className="flex justify-between items-center">
        <p className="font-semibold relative text-2xl ml-4 mt-4 mb-4 text-[#444444]">
          Consent Request Details
        </p>
        <button onClick={handleOpen} className="inline-flex gap-[15px] px-[1.05rem] m-2 py-[0.25rem] h-[2.8rem] justify-center items-center text-white w-fit hover:bg-[#276059] bg-[#006666] rounded-[10px]">
          <MdAdd className="h-[35px] w-[35px] m-auto" />
          <div className="relative w-fit font-semibold m-auto text-[20px]">
            Add Appointment
          </div>
        </button>

        <Dialog open={open} handler={handleOpen} size="lg">
              <DialogHeader>New Appointment</DialogHeader>
              <div className="h-[1px] bg-[#827F7F82]"></div>
              <DialogBody>
                <AddAppointmentForm />
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
                
                <Button variant="filled" className="bg-[#FFA000]" onClick={handleOpen}>
                  <span>Confirm</span>
                </Button>
              </DialogFooter>
            </Dialog>
      </div>
      <div className="h-[1px] bg-[#827F7F82]"></div>
      <div className="sm:rounded-lg 2xl:max-h-[500px] 4xl:max-h-[800px] lg:max-h-[50px] flex flex-col overflow-auto">
        <table className="w-full text-sm text-left rtl:text-right text-gray-500">
          <thead className="text-xs text-gray-700 uppercase h-[4.5rem] bg-gray-50 bg-[#F5F6F8] text-[#7B7878] sticky top-0">
            <tr className="text-sm">
              <th scope="col" className="px-6 py-3">
                Patient Name
              </th>
              <th scope="col" className="px-6 py-3">
                ABHA ID
              </th>
              <th scope="col" className="px-6 py-3">
                Mobile Number
              </th>
              <th scope="col" className="px-6 py-3">
                Email ID
              </th>
              <th scope="col" className="px-6 py-3 text-right">
                Details
              </th>
            </tr>
          </thead>
          <tbody className="text-sm text-[#444]">
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>

            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
            <tr className="bg-white border ">
              <th
                scope="row"
                className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
              >
                Mukensh
              </th>
              <td className="px-6 py-4">123456789</td>
              <td className="px-6 py-4">12345</td>
              <td className="px-6 py-4">titan18rex@gmail.com</td>
              <td className="px-6 py-4 text-right">
                <button
                  className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                        border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
                >
                  <div>View</div>
                  <FaCaretRight className="h-[25px] w-[25px]" />
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default PastRecords