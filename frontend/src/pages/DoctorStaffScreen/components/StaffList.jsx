import { MdSearch } from "react-icons/md";
import AddDoctorForm from "../forms/AddDoctorForm";
import StaffDetail from "./StaffDetail";
import { useEffect, useState } from "react";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import useAuth from "../../../hooks/useAuth";
import { jwtDecode } from "jwt-decode";

const StaffList = () => {
  const [allStaffList, setAllStaffList] = useState([]);
  const axiosPrivate = useAxiosPrivate();
  const [search, setSearch] = useState("");
  const { auth } = useAuth();
  const decoded = auth?.accessToken ? jwtDecode(auth.accessToken) : undefined;
  const [role, setRole] = useState("");

  useEffect(() => {
    const getStaffDetails = async () => {
      try {
        const response = await axiosPrivate.get(
          `http://127.0.0.1:9005/auth/getAllStaffList`
        );
        // console.log(response.data);
        setAllStaffList(response.data);
      } catch (err) {
        if (!err?.response) {
          // console.error("No Server Response");
        }
      }
    };
    getStaffDetails();
    setRole(decoded?.role.toLowerCase());

  }, []);
  return (
    <div className="flex flex-col h-full">
      <div className="basis-[15%]">
        <div className="text-[1.75rem] p-4 text-[#02685A] font-semibold flex flex-row m-4 justify-between items-center">
          <div className="relative">All Staff List</div>
          {role === "head_doctor" && (
            <>
              <AddDoctorForm />
            </>
          )}
        </div>
      </div>
      <div className="flex flex-row m-4 justify-between items-center">
        <div className="relative">
          <input
            className="m-4 h-[3.375rem] border-[#827F7F82] rounded-md focus:outline-none focus:ring focus:ring-[#02685A] focus:ring-opacity-70"
            type="text"
            placeholder="Search Staff"
            onChange={(e) => setSearch(e.target.value)}
          />
          <div className="absolute inset-y-0 right-2 flex items-center pr-4">
            <MdSearch className="h-[25px] w-[25px]" />
          </div>
        </div>
      </div>
      <div className="bg-[#827F7F82]  h-[0.5px] mx-8 my-4"></div>

      <div className="basis-[85%]">
        <div className="m-4 shadow-lg sm:rounded-lg 2xl:max-h-[500px] 4xl:max-h-[800px] lg:max-h-[500px] md:max-h-[450px] flex flex-col overflow-auto">
          <table className="w-full text-sm text-left rtl:text-right text-gray-500">
            <thead className="text-xs uppercase h-[4.5rem] bg-[#F5F6F8] text-[#7B7878] sticky top-0">
              <tr className="text-sm">
                <th scope="col" className="px-6 py-3">
                  S.No.
                </th>
                <th scope="col" className="px-6 py-3">
                  Name
                </th>
                {/* <th scope="col" className="px-6 py-3">
                  Registration Number
                </th> */}
                <th scope="col" className="px-6 py-3">
                  Mobile Number
                </th>
                {/* <th scope="col" className="px-6 py-3">
                  Email Address
                </th> */}
                <th scope="col" className="px-6 py-3">
                  Role
                </th>
                <th scope="col" className="px-6 py-3 text-right">
                  Details
                </th>
              </tr>
            </thead>
            <tbody className="text-sm text-[#444]">
              {allStaffList.filter((staff) => {
                  return search.toLowerCase() === ""
                    ? staff
                    : staff.first_Name.toLowerCase().includes(search.toLowerCase()) ||
                    staff.last_Name.toLowerCase().includes(search.toLowerCase());
                }).map((staff, index) => (
                <tr key={index} className="bg-white border">
                  <td className="px-6 py-4">{index + 1}</td>
                  <td className="px-6 py-4 font-medium text-[#444] whitespace-nowrap">
                    {staff.first_Name} {staff.last_Name}
                  </td>
                  <td className="px-6 py-4">{staff.mobile}</td>
                  <td className="px-6 py-4">{staff.address}</td>
                  <td className="px-6 py-4 text-right">
                    <StaffDetail staff={staff} />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};
export default StaffList;
