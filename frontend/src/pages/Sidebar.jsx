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
import { PiStethoscope } from "react-icons/pi";
import { useNavigate } from "react-router-dom";
import useLogout from "../hooks/useLogout";
import { useState, useEffect } from "react";
import { Calendar } from "antd";
import AllPatientList from "./PatientScreen/components/AllPatientList";
import { Settings } from "@mui/icons-material";
import EditTab from "./Settings/components/EditTab";

const Sidebar = () => {
  const [role, setRole] = useState("");
  const [username, setUsername] = useState("");
  const [activeTab, setActiveTab] = useState("Calender");
  useEffect(() => {
    setRole(localStorage.getItem("role"));
    setUsername(localStorage.getItem("username"));
  }, []);

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
          <BiSolidCalendarPlus className="w-7 h-7 mr-2" /> Calendar
        </span>
        // "Calendar"
      ),
      value: "Calender",
      desc: <Calendar />,
    },
    {
      label: (
        <span className="flex font-semibold flex-start">
          <FaUserInjured className="w-7 h-7 mr-2" /> All Patient
        </span>
      ),
      value: "All Patients",
      desc: <AllPatientList />,
    },
    {
      label: (
        <span className="flex font-semibold flex-start">
          <FaUserDoctor className="w-7 h-7 mr-2" /> Doctor List
        </span>
      ),
      value: "All Doctors",
      desc: <AllPatientList />,
    },
    {
      label: (
        <span className="flex font-semibold flex-start">
          <IoMdSettings className="w-8 h-8 mr-2" /> Settings
        </span>
      ),
      value: "Settings",
      desc: role === "HEAD_DOCTOR" ? <EditTab /> : <Settings />,
    },
  ];
  return (
    <>
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
            <div className="w-full pt-5 h-[400px] ">
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
                        onClick={() => setActiveTab(value)}
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
    </>
  );
};

export default Sidebar;
