import { MdSearch, MdAdd } from "react-icons/md";
import { FaCaretRight } from "react-icons/fa";
import React from "react";

import AddPatientForm from "../forms/AddPatientForm";

const AllPatientList = () => {
  
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
          <AddPatientForm />
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
