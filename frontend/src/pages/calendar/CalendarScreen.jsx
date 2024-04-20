import Sidebar from "../Sidebar";
import React, { useState } from "react";
import Calendar from "./Calendar";
// import Calendar from "./Calendar";
import moment from "moment";
import "./index.css";

const CalendarScreen = () => {
  const event = [
    {
      start: moment("2024-04-19T10:00:00").toDate(),
      end: moment("2024-04-19T12:00:00").toDate(),
      title: "MRI",
    },
    {
      start: moment("2024-04-19T14:00:00").toDate(),
      end: moment("2024-04-19T16:00:00").toDate(),
      title: "LMAO",
    },
  ];

  return (
    <div className="flex flex-row w-full">
      <div className="w-fit">
        <Sidebar />
      </div>
      <div className="flex flex-col w-full">
        <div className="w-full h-[4.5rem] flex justify-between p-2">
          <div className="flex items-center">
            <select
              className="bg-[#006666] rounded-[10px] text-white appearance-none font-semibold text-m"
              name="doctor"
            >
              <option className="font-light text-s option-hover" value="don">
                B. Rahul
              </option>
              <option value="ew" className="font-light text-s option-hover">
                Dibyarup Pal
              </option>
              <option value="lmao" className="font-light text-s option-hover">
                Shubham Zanzad
              </option>
              <option value="lol" className="font-light text-s option-hover">
                Siddharth Pillai
              </option>
            </select>
          </div>
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
        <div className="w-full h-full objext-contain p-2">
          <Calendar events={event} defaultView={"week"} />
        </div>
      </div>
    </div>
  );
};
export default CalendarScreen;
