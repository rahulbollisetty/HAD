import React, { useState } from "react";
import {
  Tabs,
  TabsHeader,
  TabsBody,
  Tab,
  TabPanel,
} from "@material-tailwind/react";
import PatientRegistrationLogs from "./PatientRegistrationLogs";
import RecordDeletionLogs from "./RecordDeletionLogs";

export default function LogsTab() {
  const [activeTab, setActiveTab] = useState("Patient Registration Details");
  const data = [
    {
      label: "Patient Registration Details",
      value: "Patient Registration Details",
      desc: <PatientRegistrationLogs />,
    },
    {
      label: "Record Deletion Details",
      value: "Record Deletion Details",
      desc: <RecordDeletionLogs />,
    },
  ];
  return (
    <Tabs value={activeTab} className="w-full my-8">
      <TabsHeader
        className="text-sm justify-center flex items-center border border-[#7B7878] rounded-sm 
              bg-white cursor-pointer"
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
            className={activeTab === value ? "text-white" : ""}
          >
            {label}
          </Tab>
        ))}
      </TabsHeader>
      <TabsBody>
        {data.map(({ value, desc }) => (
          <TabPanel key={value} value={value} className="p-0">
            {desc}
          </TabPanel>
        ))}
      </TabsBody>
    </Tabs>
  );
}
