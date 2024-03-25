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
import AddPatientForm from "../forms/AddPatientForm";

const AllPatientList = () => {
  const [open, setOpen] = React.useState(false);

  const handleOpen = () => setOpen(!open);

  return (
    <div className="flex flex-col h-full">
      <div className="basis-[15%]">
        <div className="text-[1.75rem] m-4 p-4 text-[#02685A] font-semibold">
          All Patients List
        </div>
        <div className="flex flex-row m-4 justify-between items-center">
          <div className="relative">
            <input
              className="m-4 h-[3.375rem] border-[#827F7F82] rounded-md focus:outline-none focus:ring focus:ring-[#02685A] focus:ring-opacity-70"
              type="text"
              placeholder="Search Patients"
              name=""
              id=""
            />
            <div className="absolute inset-y-0 right-2 flex items-center pr-4">
              <MdSearch className="h-[25px] w-[25px]" />
            </div>
          </div>
          <div className="">
            <button
              onClick={handleOpen}
              className="inline-flex gap-[15px] px-[1.25rem] m-4 py-[0.625rem] h-[3.375rem] justify-center items-center text-white w-fit hover:bg-[#276059] bg-[#006666] rounded-[10px]"
            >
              <MdAdd className="h-[35px] w-[35px] m-auto" />
              <div className="relative w-fit font-semibold m-auto text-[20px]">
                Add Patients
              </div>
            </button>

            <Dialog open={open} handler={handleOpen} size="md">
              <DialogHeader>Add Patient</DialogHeader>
              <div className="h-[1px] bg-[#827F7F82]"></div>
              <DialogBody>
                <AddPatientForm />
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
        </div>
      </div>
      <div className="basis-[85%]">
        <div className="m-4 shadow-lg sm:rounded-lg 2xl:max-h-[500px] 4xl:max-h-[800px] lg:max-h-[50px] flex flex-col overflow-auto">
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
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};
export default AllPatientList;
