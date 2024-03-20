import {
    Tabs,
    TabsHeader,
    TabsBody,
    Tab,
    TabPanel,
  } from "@material-tailwind/react";
import { useState } from "react";
import AddRecords from "./AddRecords";  
import ConsentTable from "./ConsentTable";
import PastRecords from "./PastRecords";
  export default function DoctorTab() {
    const [activeTab, setActiveTab] = useState("Past Records");
    const data = [
      {
        label: "Past Records",
        value: "Past Records",
        desc: <PastRecords />,
      },
      {
        label: "Import Records",
        value: "Import Records",
        desc: <ConsentTable />,
      },
      {
        label: "Add Records",
        value: "Add Records",
        desc: <AddRecords />,
      },
      
    ];
   
    return (
      <Tabs value={activeTab} className= "w-full mx-2">
        <TabsHeader className="text-sm justify-center flex items-center border border-[#7B7878] rounded-l-sm 
                bg-white cursor-pointer"
        indicatorProps={{
          className:
            "text-sm justify-center flex items-center bg-[#006666] cursor-pointer",
        }}>
          {data.map(({ label, value }) => (
            <Tab key={value} value={value} 
            onClick={() => setActiveTab(value)}
              className={activeTab === value ? "text-white" : ""}>
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