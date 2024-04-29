import BaseScreen from "../BaseScreen";
import React, { useState } from "react";
import Sidebar from "../Sidebar";
import React, { useState, useEffect } from "react"; // Combine import statements for React
import Calendar from "./Calendar";
import moment from "moment";
import "./index.css";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import useAuth from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";
import TopBar from "../TopBar";
import { jwtDecode } from "jwt-decode";

const CalendarScreen = () => {
  const axiosPrivate = useAxiosPrivate();
  const { auth } = useAuth();
  const decoded = auth?.accessToken ? jwtDecode(auth.accessToken) : undefined;
  const navigate = useNavigate();
  
  // State variables
  const [AllDoctorList, setAllDoctorList] = useState([]);
  const [appointmentDetails, setAppointmentDetails] = useState([]);
  const [currDoctorId, setCurrDoctorId] = useState();
  const [event, setEvent] = useState([]);
  const [role, setRole] = useState("");

  useEffect(() => {
    const getAllAppointments = async () => {
      try {
        const resp = await axiosPrivate.get("http://127.0.0.1:9005/patient/appointment/getAllAppointments");
        setAppointmentDetails(resp.data);
      } catch (error) {
        console.error("Error fetching appointments:", error);
      }
    };
    
    const getAllDoctorList = async () => {
      try {
        const resp = await axiosPrivate.get("http://127.0.0.1:9005/doctor/getAllDoctorList");
        setAllDoctorList(resp.data);
      } catch (error) {
        console.error("Error fetching doctor list:", error);
      }
    };
    
    getAllAppointments();
    getAllDoctorList();
    setRole(decoded?.role);
    // if(decoded?.role === "DOCTOR" || decoded?.role === "HEAD_DOCTOR")
    //   setCurrDoctorName(decoded?.name);
    if(decoded?.role === "DOCTOR" || decoded?.role === "HEAD_DOCTOR") {
      setCurrDoctorName(decoded?.name);
    }
  }, []);

  useEffect(() => {
    handleEvent();
  }, [currDoctorId]);
  }, [appointmentDetails, currDoctorName]);

  const add15Minutes = (datetimeString) => {
    const dateTime = moment(datetimeString);
    const newDateTime = dateTime.add(15, "minutes");
    return newDateTime.format("YYYY-MM-DDTHH:mm:ss");
  };

  const handleEvent = () => {
    const doctorAppointments = appointmentDetails.filter(
      (appointment) => appointment.doctor_id === currDoctorId
      (appointment) => appointment.doctor_name === currDoctorName
    );
    console.log(doctorAppointments)
    const newEvents = doctorAppointments.map((app) => ({
      start: moment(app.date + "T" + app.time).toDate(),
      end: moment(add15Minutes(app.date + "T" + app.time)).toDate(),
      title:  app.notes+" (Dr. "+app.doctor_name+")",
      appointment_id: app.appointment_id,
      status: app.status,
      patientId: app.patientId?.mrn,
      doctorId:app.doctor_id,
      appStatus:app.status
    }));
    setEvent(newEvents);
  };

  const handleDoctorChange = (id) => {
    setCurrDoctorId(id);
    handleEvent();
  };
  

  const handleEventClick = (event) => {
    navigate(`/patientScreen/${event.patientId}`,{ state: { appointmentId: event.appointment_id,appointmentStatus:event.appStatus } });
    navigate(0);
  };

  const eventStyleGetter = (event) => {
    const colorMap = {};
    AllDoctorList.forEach((doctor, index) => {
      const hue = (360 / AllDoctorList.length) * index;
      colorMap[doctor.doctor_Id] = `hsl(${hue}, 50%, 50%)`;
    });
    const backgroundColor = colorMap[event.doctorId] || '#FFD700'; // Default color
    console.log(backgroundColor)
    return {
      style: {
        backgroundColor,
      },
    };
    navigate(`/patientScreen/${event.patientId}/appointment/${event.appointment_id}`);
  };

  return (
     
        <div className="flex flex-col w-full m-4 bg-white">
          <div className="w-full h-[4.5rem] flex justify-between">
            <div className="flex items-center">
              {role == "DOCTOR" && (
                <select
                  className="bg-[#006666] rounded-[10px] text-white appearance-none font-semibold text-m"
                  name="doctor"
                  value={currDoctorId}
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
                      value={doctor.doctor_Id}
                    >
                      {doctor.first_Name} {doctor.last_Name}
                    </option>
                  ))}
                </select>
              )}
            </div>
          </div>

          <div className="w-full">
            <Calendar
              events={event}
              defaultView={"week"}
              eventPropGetter={eventStyleGetter}
              onSelectEvent={handleEventClick}
              style={{height: 800}}
            />
          </div>
        </div>
  );
};

export default CalendarScreen;
