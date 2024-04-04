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

  return (
    <div className="border mx-3 my-4 border-[#006666] rounded-md border-l-4">
      <div className="flex justify-between items-center">
        <p className="font-semibold relative text-2xl ml-4 mt-4 mb-4 text-[#444444]">
          All Appointment Details
        </p>
        <AddAppointmentForm />
      </div>
      <div className="h-[1px] bg-[#827F7F82]"></div>
      <div className="sm:rounded-lg 2xl:max-h-[500px] 4xl:max-h-[800px] lg:max-h-[50px] flex flex-col overflow-auto">
        <table className="w-full text-sm text-left rtl:text-right text-gray-500">
          <thead className="text-xs text-gray-700 uppercase h-[4.5rem] bg-gray-50 bg-[#F5F6F8] text-[#7B7878] sticky top-0">
            <tr className="text-sm">
              <th scope="col" className="px-6 py-3">
                Doctor Name
              </th>
              <th scope="col" className="px-6 py-3">
                Appointment Date
              </th>
              <th scope="col" className="px-6 py-3">
                Appointment Time
              </th>
              <th scope="col" className="px-6 py-3">
                Status
              </th>
              
              <th scope="col" className="px-6 py-3">
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Upcoming</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Upcoming</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Upcoming</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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
                Dr. Dibyarup Pal
              </th>
              <td className="px-6 py-4">12/04/2024</td>
              <td className="px-6 py-4">01:04 PM</td>
              <td className="px-6 py-4">Completed</td>
              <td className="px-6 py-4">Patient is suffering from chronic allegic symptoms. Tremendous sneezing and breating problem.</td>
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