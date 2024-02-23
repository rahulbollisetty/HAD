import React from "react";
import Sidebar from "../Sidebar";
import ConsentForm from "./components/ConsentForm";
import AllPatientList from "./components/AllPatientList";
import AddRecords from "./AddRecords";
import Profile from "./components/Profile";
export const PatientScreen = () => {
  return (
    <div className="flex flex-row w-full">
      <div className="w-fit">
      <Sidebar/>
      </div>
      <div  className="basis-full bg-[#F1F5FC] overflow-hidden">
        <div className="flex flex-col h-full">
          <div className='h-[64px] w-full pb-16 bg-white'></div>
          <div className="bg-white grow m-3">
            <AddRecords/>
          </div> 
        </div>
      </div>
    </div>
  );
};