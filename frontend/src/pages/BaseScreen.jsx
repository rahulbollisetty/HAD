import {
  MdOutlineCalendarToday,
  MdOutlineSettings,
  MdPeopleOutline,
  MdExitToApp,
} from "react-icons/md";
import { IoMdSettings } from "react-icons/io";
import { FaUserInjured } from "react-icons/fa";
import { BiSolidCalendarPlus } from "react-icons/bi";
import { FaUserDoctor } from "react-icons/fa6";
import {
  Tabs,
  TabsHeader,
  TabsBody,
  Tab,
  TabPanel,
} from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";
import useLogout from "../hooks/useLogout";
import { useState, useEffect } from "react";
import AllPatientList from "./PatientScreen/components/AllPatientList";

import { useParams, useLocation } from "react-router-dom";
import { PatientScreen } from "./PatientScreen/PatientScreen";
import { DoctorStaffScreen } from "./DoctorStaffScreen/DoctorStaffScreen";
import CalendarScreen from "./calendar/CalendarScreen";
import SettingsScreen from "./Settings/SettingsScreen";
// import { jwtDecode } from "jwt-decode";
// import useAuth from "../hooks/useAuth";

const BaseScreen = ({ active_tab }) => {
  // const [username, setUsername] = useState("");
  const [activeTab, setActiveTab] = useState(active_tab);
  const { id,appId } = useParams();
  
  const location = useLocation();
  const currentUrl = location.pathname;
  const { appointmentId,appointmentStatus } = location.state || {};
  // console.log(id, currentUrl);
  // const { auth } = useAuth();
  // const decoded = auth?.accessToken ? jwtDecode(auth.accessToken) : undefined;
  // const [role, setRole] = useState("");
  // useEffect(() => {

  //   console.log(currentUrl)
  // }, []);

  // const handleUrlChange = () => {
  //   let url;
  //   switch (activeTab) {
  //     case "Calendar":
  //       url = "/calendar";
  //       break;
  //     case "All Patient":
  //         url = "/";
  //       break;
  //     case "Doctor List":
  //       url = "/stafflist";
  //       break;
  //     case "Settings":
  //       url = "/settings";
  //       break;
  //   }
  //   window.history.pushState({}, "", url);
  // };

  // useEffect(() => {
  //   handleUrlChange();
  // }, [activeTab]);

  const navigate = useNavigate();
  const logout = useLogout();
  const signOut = async () => {
    await logout();
    navigate("/");
  };

  const data = [
    {
      label: (
        <span className="flex font-semibold flex-start">
          <BiSolidCalendarPlus className="w-7 h-7 mr-2" />
          Calendar
        </span>
        // "Calendar"
      ),
      value: "Calendar",
    },
    {
      label: (
        <span className="flex font-semibold flex-start">
          <FaUserInjured className="w-7 h-7 mr-2" />
          All Patient
        </span>
      ),
      value: "All Patient",
    },
    {
      label: (
        <span className="flex font-semibold flex-start">
          <FaUserDoctor className="w-7 h-7 mr-2" /> Doctor List
        </span>
      ),
      value: "Doctor List",
    },
    {
      label: (
        <span className="flex font-semibold flex-start">
          <IoMdSettings className="w-8 h-8 mr-2" /> Settings
        </span>
      ),
      value: "Settings",
    },
  ];

  const handleToggleTab = (v) => {
    setActiveTab(v);
    let navigateTo = "";
    switch (v) {
      case "Doctor List":
        navigateTo = "stafflist";
        break;
      case "Settings":
        navigateTo = "settings";id
        break;
      case "Calendar":
        navigateTo = "calendar";
        break;
    }
    navigate(`/${navigateTo}`);
  };
  return (
    <>
      <div className="flex flex-row w-full">
        <div className="w-fit">
          <div className="h-[100vh] top-0  w-[250px] left-0">
            <div className="h-[64px] bg-black">
              <div className="h-full [font-family:'Inter',Helvetica] font-bold text-white text-[34px] flex items-center justify-center">
                MediSync
              </div>
            </div>
            <div className="h-4/5">
              <div className="flex flex-col">
                {/* <div className="h-[80px] inactive align-center">
              <div className="inline-flex gap-[15px]  w-fit relative left-7 top-1/3">
                <MdOutlineCalendarToday className="h-[25px] w-[25px] m-auto" />
                <div className="relative w-fit font-semibold text-[20px]">
                  Schedule
                </div>
              </div>
            </div>
            <div className="h-[80px] active align-center">
              <div className="inline-flex gap-[15px] w-fit relative left-[30px] top-1/3">
                <MdPeopleOutline className="h-[25px] w-[25px] m-auto" />
                <div className="relative w-fit font-semibold text-[20px]">
                  Patients
                </div>
              </div>
            </div>
            <div className="h-[80px] inactive align-center">
              <div className="inline-flex gap-[15px]  w-fit relative left-[30px] top-1/3">
                <PiStethoscope className="h-[25px] w-[25px] font-semibold m-auto" />
                <div className="relative w-fit font-semibold text-[20px]">
                  Doctors
                </div>
              </div>
            </div>

            <a href="/settings" className="h-[80px] inactive align-center">
              <div className="inline-flex gap-[15px] w-fit relative left-[30px] top-1/3">
                <MdOutlineSettings className="h-[25px] w-[25px] m-auto" />
                <div className="relative w-fit font-semibold text-[20px]">
                  Settings
                </div>
              </div>
            </a> */}
                <div className="w-full pt-5 h-[450px] ">
                  {/* <div className="text-[#02685A] text-3xl font-bold p-0 ml-10 w-1/5 flex flex-col justify-center items-center">
                <p>Edit Details</p>
              </div> */}
                  <div className="h-full flex flex-col justify-center items-center">
                    <Tabs value={activeTab} className="w-full">
                      <TabsHeader
                        className="flex-col bg-white"
                        indicatorProps={{
                          className: "bg-[#c6fff282] active cursor-pointer",
                        }}
                      >
                        {data.map(({ label, value }) => (
                          <Tab
                            key={value}
                            value={value}
                            onClick={() => handleToggleTab(value)}
                            className={
                              activeTab === value
                                ? "h-[80px] text-[#02685A] text-[1.3rem]"
                                : "text-[#7B7878] h-[80px] text-[1.3rem]"
                            }
                          >
                            <div className="flex justify-center items-center">
                              {/* <MdOutlineCalendarToday className="h-[25px] w-[25px] m-auto" /> */}
                              <div className="absolute pl-8 left-0 font-semibold text-[1.3rem]">
                                {label}
                              </div>
                            </div>
                          </Tab>
                        ))}
                      </TabsHeader>
                    </Tabs>
                  </div>
                </div>
              </div>
            </div>
            <div className="h-1/12 m-[10px]">
              <div className="inline-flex gap-[15px] px-[25px] py-[10px] text-white w-fit relative left-[15px] top-1/3 bg-[#006666] rounded-[10px] overflow-hidden shadow-[-2px_-1px_3px_#00000040]">
                <MdExitToApp className="h-[25px] w-[25px] m-auto" />
                <div
                  className="relative w-fit font-semibold text-[20px]"
                  onClick={signOut}
                >
                  Logout
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="basis-full bg-[#F1F5FC] overflow-hidden">
          <div className="flex flex-col h-full">
            <div className="h-[64px] w-full pb-16 bg-white"></div>
            <div className="bg-white grow m-3">
              {/* <div className="flex flex-col">
              <Profile patientId={id}/>
              <div className="">
                <div className="flex mt-6 mb-8  justify-center">
                  <DoctorTab tab={tab} patientId={id}/>
                </div>
              </div>
            </div> */}
              {activeTab === "Calendar" ? (
                <CalendarScreen />
              ) : activeTab === "All Patient" ? (
                <>
                {id && <>{currentUrl === `/patientScreen/${id}` && <PatientScreen appId={appointmentId} appStatus={appointmentStatus} id={id}/>}</>}
                {!id && <><AllPatientList /></>}
                </>
              ) : activeTab === "Doctor List" ? (
                <DoctorStaffScreen />
              ) : activeTab === "Settings" ? (
                <SettingsScreen />
              ) : (
                <p>Invalid case number</p>
              )}
              
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default BaseScreen;
