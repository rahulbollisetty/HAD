import {
  Tabs,
  TabsHeader,
  TabsBody,
  Tab,
  TabPanel,
} from "@material-tailwind/react";
import { useState, useEffect } from "react";
import PracticeEdit from "./PracticeEdit";
import DoctorEdit from "./DoctorEdit";
import Sidebar from "../../Sidebar";
import StaffEdit from "./StaffEdit";
import { jwtDecode } from "jwt-decode";
import useAuth from "../../../hooks/useAuth";

const EditTab = () => {
  const [activeTab, setActiveTab] = useState("Practice Details");
  const [role, setRole] = useState("");
  const { auth } = useAuth;
  const decoded = auth?.accessToken ? jwtDecode(auth.accessToken) : undefined;
  useEffect(() => {
    setRole(decoded?.role);
  }, []);
  const data = [
    {
      label: "Practice Details",
      value: "Practice Details",
      desc: <PracticeEdit />,
    },
    {
      label: "Your Details",
      value: "Your Details",
      desc: <StaffEdit />,
    },
  ];

  return (
    <div className="flex flex-row w-full">
      <div className="w-fit">
        <Sidebar  />
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
                Dr. B. Rahul
              </p>
            </div>
          </div>
        </div>
        <div className="basis-full bg-[#F1F5FC] overflow-hidden">
          <div className="bg-white  m-5 pr-5">
            <div className="w-full flex flex-row pt-5">
              {/* <div className="text-[#02685A] text-3xl font-bold p-0 ml-10 w-1/5 flex flex-col justify-center items-center">
                <p>Edit Details</p>
              </div> */}
              <div className="flex-grow flex justify-center items-center ">
                <Tabs value={activeTab} className="w-9/12 mx-2">
                  <TabsHeader
                    className="text-sm justify-center flex items-center border border-[#7B7878] rounded-l-sm bg-white cursor-pointer"
                    indicatorProps={{
                      className:
                        "text-sm justify-center flex items-center bg-[#006666] cursor-pointer",
                    }}
                  >
                    {data.map(({ label, value }) => (
                      <Tab
                        key={value}
                        value={value}
                        onClick={() => setActiveTab(value)}
                        className={
                          activeTab === value ? "text-white" : "text-black"
                        }
                      >
                        {label}
                      </Tab>
                    ))}
                  </TabsHeader>
                </Tabs>
              </div>
            </div>
            <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
            <div className="flex flex-row  w-full m-5">
              <div className="w-full">
                {data.map(
                  ({ value, desc }) =>
                    activeTab === value && (
                      <div key={value} className="p-0">
                        {desc}
                      </div>
                    )
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default EditTab;
