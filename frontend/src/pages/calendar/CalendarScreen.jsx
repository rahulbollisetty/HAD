import Sidebar from "../Sidebar";
import React, { useState } from "react";
import Calendar from "./Calendar";
import moment from "moment";
import "./index.css";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useEffect } from "react";
import useAuth from "../../hooks/useAuth";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";
import TopBar from "../TopBar";

const CalendarScreen = () => {
  const axiosPrivate = useAxiosPrivate();
  const [AllDoctorList, setAllDoctorList] = useState([]);
  const [appointmentDetails, setAppointmentDetails] = useState([]);
  const [currDoctorName, setCurrDoctorName] = useState("");
  const [event, setEvent] = useState([]);
  const [role, setRole] = useState("");
  const { auth } = useAuth();
  const decoded = auth?.accessToken ? jwtDecode(auth.accessToken) : undefined;
  const navigate = useNavigate();

  useEffect(() => {
    // setCurrDoctorId()

    const getAllAppointments = async () => {
      role;
      const resp = await axiosPrivate.get(
        "http://127.0.0.1:9005/patient/appointment/getAllAppointments"
      );
      console.log(resp.data)
      setAppointmentDetails(resp.data);
    };
    getAllAppointments();

    const getAllDoctorList = async () => {
      const resp = await axiosPrivate.get(
        "http://127.0.0.1:9005/doctor/getAllDoctorList"
      );
      setAllDoctorList(resp.data);
    };
    getAllDoctorList();
    setRole(decoded?.role);
    if(decoded?.role === "DOCTOR" || decoded?.role === "HEAD_DOCTOR")
      setCurrDoctorName(decoded?.name);
  }, []);

  useEffect(() => {
    handleEvent();
  }, [currDoctorName]);

  const add15Minutes = (datetimeString) => {
    const dateTime = moment(datetimeString);
    const newDateTime = dateTime.add(15, "minutes");
    return newDateTime.format("YYYY-MM-DDTHH:mm:ss");
  };

  const handleEvent = () => {
    const doctorAppointments = appointmentDetails.filter(
      (appointment) => appointment.doctor_name == currDoctorName
    );
    const newEvents = doctorAppointments.map((app) => ({
      start: moment(app.date + "T" + app.time).toDate(),
      end: moment(add15Minutes(app.date + "T" + app.time)).toDate(),
      title: app.notes,
      appointment_id: app.appointment_id,
      status: app.status,
      patientId: app.patientId?.mrn,
    }));
    setEvent(newEvents);
  };

  const handleDoctorChange = (index) => {
    const selectedDoctorName = AllDoctorList[index].doctor_name;
    setCurrDoctorName(selectedDoctorName);
  };

  const handleEventClick = (event) => {
    navigate(
      `/patientScreen/${event.patientId}/appointment/${event.appointment_id}`
    );
  };

  return (
    <div className="flex flex-row w-full">
      <div className="w-fit">
        <Sidebar active_tab={"Calendar"} />
      </div>
      <div className="flex flex-col w-full bg-[#F1F5FC]">
        <TopBar />
        <div className="flex flex-col w-full mt-4 bg-white">
          <div className="w-full h-[4.5rem] flex justify-between">
            <div className="flex items-center">
              {role !== "DOCTOR" && (
                <select
                  className="bg-[#006666] rounded-[10px] text-white appearance-none font-semibold text-m"
                  name="doctor"
                  onChange={(e) => handleDoctorChange(e.target.value)}
                  defaultValue=""
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
              )}
            </div>
          </div>

          <div className="w-full h-full object-contain ">
            <Calendar
              events={event}
              defaultView={"week"}
              onSelectEvent={handleEventClick}
            />
          </div>
        </div>
      </div>
    </div>
  );
};
export default CalendarScreen;
