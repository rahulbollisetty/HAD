import React from "react";
import Sidebar from "../Sidebar";
import AllPatientList from "./components/AllPatientList";
import AddRecords from "./AddRecords";
export const PatientScreen = () => {
  return (
    <div className="flex flex-row">
      <div >
      <Sidebar/>
      </div>
      <div  className="basis-full bg-[#F1F5FC] h-screen">
        <div className="flex flex-col h-full">
          <div className="h-[64px] bg-white"></div>
          <div className="bg-white grow m-3">
              <AddRecords/>
          </div>
        </div>

      </div>
    </div>
  );
};
