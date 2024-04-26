import Sidebar from "../Sidebar";
import React, { useState, useEffect } from "react";
import EditTab from "./components/EditTab";
import StaffEdit from "./components/StaffEdit";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

const SettingsScreen = () => {
  const axiosPrivate = useAxiosPrivate();
  const [userDetails, setUserDetails] = useState({});
  useEffect(() => {
    const username = localStorage.getItem("username");
    const role = localStorage.getItem("role");
    console.log(role, username)
    const requestBody = {
      username : username
    }
    const getUserDetails = async () => {
      try {
        const response = await axiosPrivate.post(
          `http://127.0.0.1:9005/auth/get-${role.toLocaleLowerCase()}-details-by-username`,
          requestBody
        );
        console.log(JSON.stringify(response?.data));
        setUserDetails(response.data);
      } catch (err) {
        if (!err?.response) {
          console.error("No Server Response");
        }
      }
    };
    getUserDetails();
  }, []);
  return (
    <div className="flex flex-row w-full">
      <div className="w-fit">
        <Sidebar />
      </div>
      <div className="flex flex-col w-full">
        <div className="w-full h-[4.5rem] flex flex-row-reverse p-2">
          <div className="flex items-center mr-[2em]">
            <div className="flex justify-center items-center h-4/5">
              <img
                className="rounded-full h-full object-contain"
                src="https://wallpapers.com/images/hd/handsome-giga-chad-hmsvijj0ji4dhedr.jpg"
                alt="profile photo"
              />
            </div>
            <div className="mx-2">
              <p className="text-[#787887] text-m font-semibold">
                {userDetails.first_Name}  {userDetails.last_Name}
              </p>
            </div>
          </div>
        </div>
        <div className="basis-full bg-[#F1F5FC] overflow-hidden">
          <div className="flex flex-col h-full">
            {/* <div className="h-[64px] w-full pb-16 bg-white"></div> */}
            <div className="bg-white grow m-3">
              <div className="flex flex-col">
                {/* <Profile patientId={id} /> */}
                <div className="">
                  <div className="flex mt-6 mb-8  justify-center">
                    <StaffEdit />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default SettingsScreen;
