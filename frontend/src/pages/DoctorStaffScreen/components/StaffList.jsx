import { MdSearch } from "react-icons/md";
import AddStaffForm from "../forms/AddStaffForm";
import StaffDetail from "./StaffDetail";

const StaffList = () => {
  return (
    <div className="flex flex-col h-full">
      <div className="basis-[15%]">
        <div className="text-[1.75rem] m-4 p-4 text-[#02685A] font-semibold flex flex-row m-4 justify-between items-center">
          <div className="relative">All Staff List</div>
          <AddStaffForm />
        </div>
      </div>
      <div className="flex flex-row m-4 justify-between items-center">
        <div className="relative">
          <input
            className="m-4 h-[3.375rem] border-[#827F7F82] rounded-md focus:outline-none focus:ring focus:ring-[#02685A] focus:ring-opacity-70"
            type="text"
            placeholder="Search Staff"
            name=""
            id=""
          />
          <div className="absolute inset-y-0 right-2 flex items-center pr-4">
            <MdSearch className="h-[25px] w-[25px]" />
          </div>
        </div>
      </div>
      <div className="basis-[85%]">
        <div className="m-4 shadow-lg sm:rounded-lg 2xl:max-h-[500px] 4xl:max-h-[800px] lg:max-h-[50px] flex flex-col overflow-auto">
          <table className="w-full text-sm text-left rtl:text-right text-gray-500">
            <thead className="text-xs text-gray-700 uppercase h-[4.5rem] bg-gray-50 bg-[#F5F6F8] text-[#7B7878] sticky top-0">
              <tr className="text-sm">
                <th scope="col" className="px-6 py-3">
                  S.No.
                </th>
                <th scope="col" className="px-6 py-3">
                  Name
                </th>
                <th scope="col" className="px-6 py-3">
                  Registration Number
                </th>
                <th scope="col" className="px-6 py-3">
                  Mobile Number
                </th>
                <th scope="col" className="px-6 py-3">
                  Email Address
                </th>
                <th scope="col" className="px-6 py-3">
                  Role
                </th>
                <th scope="col" className="px-6 py-3 text-right">
                  Details
                </th>
              </tr>
            </thead>
            <tbody className="text-sm text-[#444]">
              {/* {AllPatientList.map((item, index) => ( */}
              {/* <tr className="bg-white border ">
                <td
                  scope="row"
                  className="px-6 py-4 font-medium text-[#444] whitespace-nowrap"
                ></td>
                <td className="px-6 py-4"></td>
                <td className="px-6 py-4"></td>
                <td className="px-6 py-4"></td>
                <td></td>
                <td></td>
                <td className="px-6 py-4 text-right">
                  <StaffDetail />
                </td>
              </tr> */}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};
export default StaffList;
