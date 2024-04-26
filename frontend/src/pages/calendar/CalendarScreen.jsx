import Sidebar from "../Sidebar";
import React, { useState } from "react";
import Calendar from "./Calendar";
// import Calendar from "./Calendar";
import moment from "moment";
import "./index.css";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useEffect } from "react";

const CalendarScreen = () => {
  const axiosPrivate = useAxiosPrivate();
  const [AllDoctorList, setAllDoctorList] = useState([]);
  const [appointmentDetails, setAppointmentDetails] = useState([]);
  const [currDoctorId, setCurrDoctorId] = useState(0);
  const [event, setEvent] = useState([]);

  useEffect(() => {
    const getAllAppointments = async () => {
      const resp = await axiosPrivate.get(
        "http://127.0.0.1:9005/patient/appointment/getAllAppointments"
      );
      setAppointmentDetails(resp.data);
      console.log(resp.data);
    };
    getAllAppointments();

    const getAllDoctorList = async () => {
      const resp = await axiosPrivate.get(
        "http://127.0.0.1:9005/doctor/getAllDoctorList"
      );
      setAllDoctorList(resp.data);
      console.log(resp.data);
    };
    getAllDoctorList();
  }, []);

  useEffect(() => {
    const f = () => {
      handleEvent(1);
      console.log(event, "useEffect");
    };
    f();
  }, [appointmentDetails]);

  // const Event = [
  //   {
  //     start: moment("2024-04-19T10:00:00").toDate(),
  //     end: moment("2024-04-19T12:00:00").toDate(),
  //     title: "MRI",
  //     // name: "Shubham",
  //   },
  //   {Gender
  //     start: moment("2024-04-19T14:00:00").toDate(),
  //     end: moment("2024-04-19T16:00:00").toDate(),
  //     title: "LMAO",
  //   },
  // ];

  const add15Minutes = (datetimeString) => {
    const dateTime = moment(datetimeString);
    const newDateTime = dateTime.add(15, "minutes");
    return newDateTime.format("YYYY-MM-DDTHH:mm:ss");
  };
  const handleEvent = (id) => {
    console.log(appointmentDetails);
    const doctorAppointments = appointmentDetails.filter(
      (appointment) => parseInt(appointment.doctor_id) === id
    );
    setEvent([]);
    console.log(doctorAppointments);
    doctorAppointments.forEach((app) => {
      const startTime = app.date + "T" + app.time;
      const newStartTime = moment(startTime).toDate();
      const endTime = add15Minutes(startTime);
      const newEndTime = moment(endTime).toDate();
      const newEvent = {
        start: newStartTime,
        end: newEndTime,
        title: app.notes,
      };
      setEvent((event) => [...event, newEvent]);
    });
  };

  const handleDoctorChange = (index) => {
    const selectedDoctorId = AllDoctorList[index].doctor_Id;
    setCurrDoctorId(selectedDoctorId);
    console.log(selectedDoctorId);
    handleEvent(selectedDoctorId);
  };
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
              onChange={(e) => handleDoctorChange(e.target.value)}
              defaultValue="" // Set the default value to an empty string
            >
              <option disabled value="">
                Select doctor
              </option>
              {AllDoctorList.map((doctor, index) => (
                <option
                  key={index}
                  className="font-light text-s option-hover"
                  value={index}
                >
                  {doctor.first_Name} {doctor.last_Name}
                </option>
              ))}
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
