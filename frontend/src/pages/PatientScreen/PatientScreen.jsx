import React from "react";
import Sidebar from "../Sidebar";
import ConsentForm from "./components/ConsentForm";
import AllPatientList from "./components/AllPatientList";
import AddRecords from "./components/AddRecords";
import Profile from "./components/Profile";
import DoctorTab from "./components/Tabs";

export const PatientScreen = () => {
  return (
    <div className="flex flex-row w-full">
      <div className="w-fit">
        <Sidebar />
      </div>
      <div className="basis-full bg-[#F1F5FC] overflow-hidden">
        <div className="flex flex-col h-full">
          <div className="h-[64px] w-full pb-16 bg-white"></div>
          <div className="bg-white grow m-3">
            {/* <div className="flex flex-col">
              <Profile />
              <div className="">
                <div className="flex mt-6 mb-8  justify-center">
                  <DoctorTab />
                </div>
              </div>
            </div> */}
            <AllPatientList/>
          </div>
        </div>
      </div>
    </div>
  );
};
