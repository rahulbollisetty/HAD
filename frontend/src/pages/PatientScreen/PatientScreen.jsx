import React, { useEffect } from "react";
import Profile from "./components/Profile";
import DoctorTab from "./components/Tabs";
import { useParams } from "react-router-dom";

export const PatientScreen = (tab) => {
  const id = tab.id;
// console.log(tab)
  return (
    <div className="flex flex-col">
      <Profile patientId={id} />
      <div className="">
        <div className="flex mt-6 mb-8 flex-grow justify-center">
          <DoctorTab patientId={id} appointmentId={tab.appId} appointmentStatus={tab.appStatus}/>
        </div>
      </div>
    </div>
  );
};
