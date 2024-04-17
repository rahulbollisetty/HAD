import React, { useEffect } from "react";
import Sidebar from "../Sidebar";
import Profile from "./components/Profile";
import DoctorTab from "./components/Tabs";
import { useParams} from 'react-router-dom';


  


export const PatientScreen = (tab) => {

  const { id } = useParams();
  
 
  return (
    <div className="flex flex-row w-full">
      <div className="w-fit">
        <Sidebar />
      </div>
      <div className="basis-full bg-[#F1F5FC] overflow-hidden">
        <div className="flex flex-col h-full">
          <div className="h-[64px] w-full pb-16 bg-white"></div>
          <div className="bg-white grow m-3">
            <div className="flex flex-col">
              <Profile patientId={id}/>
              <div className="">
                <div className="flex mt-6 mb-8  justify-center">
                  <DoctorTab tab={tab} patientId={id}/>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
