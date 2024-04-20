import {
  Tabs,
  TabsHeader,
  TabsBody,
  Tab,
  TabPanel,
} from "@material-tailwind/react";
import { useState } from "react";
import PracticeEdit from "./PracticeEdit";
import DoctorEdit from "./DoctorEdit";

const EditTab = () => {
  const [activeTab, setActiveTab] = useState("Practice Details");
  const data = [
    {
      label: "Practice Details",
      value: "Practice Details",
      desc: <PracticeEdit />,
    },
    {
      label: "Your Details",
      value: "Your Details",
      desc: <DoctorEdit />,
    },
  ];

  return (
    <div className="w-full flex flex-col">
      <div className="w-full flex flex-row">
        <div className="text-[#02685A] text-3xl font-bold p-0 ml-10 w-1/5 flex flex-col justify-center items-center">
          <p>Edit Details</p>
        </div>
        <div className="flex-grow flex justify-center items-center">
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
                  className={activeTab === value ? "text-white" : "text-black"}
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
  );
};
export default EditTab;
