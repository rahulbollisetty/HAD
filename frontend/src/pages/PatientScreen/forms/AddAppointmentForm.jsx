import React, { useState } from "react";
import { useForm } from "react-hook-form";
import AbhaRegister from "./AbhaRegister";
import AbhaVerify from "./AbhaVerify";

function AddAppointmentForm() {
  const {
    register,
    handleSubmit,
    reset,
    getValues,
    formState: { errors },
  } = useForm();

  return (
    <div className="2xl:max-h-[43rem] 4xl:max-h-[70rem] overflow-auto p-4">
      <div>
        <p className="text-xl text-[#353535]">Appointment</p>
      </div>
      <hr className="h-[2px] bg-[#7B7878] mt-2 opacity-50	" />
      <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl mt-8">
        <div>
          <div className="flex flex-col">
            <p className="text-sm pb-2">Doctor Name</p>
            <select className="w-full rounded-md" name="doctorName" id="">
              <option   >Select Doctor</option>
              <option value="A+">Doctor A</option>
              <option value="A-">Doctor B</option>
              <option value="B+">Doctor C</option>
              <option value="B-">Doctor D</option>
            </select>
          </div>
        </div>
        <div className="flex flex-col"></div>
        <div className="flex flex-col"></div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm pb-2">Scheduled On</p>
            <input className="rounded-md" type="date" name="scheduledOn" id="" />
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm pb-2">Time</p>
            <input className="rounded-md" type="time" name="time" id="" />
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm pb-2">Duration</p>
            <input className="rounded-md" type="text" name="duration" id="" />   
          </div>
        </div>
      </div>
      <div className="grid grid-cols-1  gap-5 text-[#7B7878] font-medium text-xl">
        <div class="flex flex-col mt-4">
          <p className="text-sm pb-2">Notes</p>
          <textarea className="rounded-md w-full"  name="notes" id=""> </textarea>
        </div>
      </div>
          
      <hr className="h-[3px] bg-[#7B7878] mt-8 mb-2 opacity-50	" />
      <div>
        <p className="text-xl text-[#353535]">Vital Signs</p>
      </div>
      <hr className="h-[2px] bg-[#7B7878] mt-2 mb-2 opacity-50	" />
      <div className="grid grid-cols-4 gap-5 text-[#7B7878] font-medium	text-xl mt-8">
        <div className="flex flex-col">
          <p className="text-sm pt-2.5">Weight (Kg)</p>
        </div>
        <div className="flex flex-col">
          <input className="rounded-md" type="number" name="" id="" />
        </div>
        <div className="flex flex-col">
          <p className="text-sm pt-2.5">Height (cms)</p>
        </div>
        <div className="flex flex-col"> 
          <input className="rounded-md" type="number" name="" id="" />
        </div>
        
        <div className="flex flex-col">
          <p className="text-sm pt-2.5">Age</p>
        </div>
        <div className="flex flex-col">
          <input className="rounded-md" type="number" name="" id="" />
        </div>
        <div className="flex flex-col">
          <p className="text-sm pt-2.5">Temperature (Degree)</p>
        </div>
        <div className="flex flex-col"> 
          <input className="rounded-md" type="number" name="" id="" />
        </div>
        
        <div className="flex flex-col">
          <p className="text-sm pt-2.5">Blood Pressure (mmHg)</p>
        </div>
        <div className="flex flex-col">
          <div class="flex">
            <input className="rounded-md" type="number" name="" id="" placeholder="Systolic" />
            <p className="text-3xl pt-1 pl-4 pr-5"> / </p>
            <input className="rounded-md" type="number" name="" id="" placeholder="Diastolic" />
          </div>
        </div>
        <div className="flex flex-col">
        </div>
        <div className="flex flex-col"> 
        </div>
        
        <div className="flex flex-col">
          <p className="text-sm pt-2.5">Pulse (Heart beats/min)</p>
        </div>
        <div className="flex flex-col">
          <input className="rounded-md" type="number" name="" id="" />
        </div>
        <div className="flex flex-col">
          <p className="text-sm pt-2.5">Resp. rate (beats/min)</p>
        </div>
        <div className="flex flex-col"> 
          <input className="rounded-md" type="number" name="" id="" />
        </div>
        
        <div className="flex flex-col">
          <p className="text-sm pt-2.5">Total Cholesterol (mg/dL)</p>
        </div>
        <div className="flex flex-col">
          <input className="rounded-md" type="number" name="" id="" />
        </div>
        <div className="flex flex-col">
          <p className="text-sm pt-2.5">Triglycerides (mg/dL)</p>
        </div>
        <div className="flex flex-col"> 
          <input className="rounded-md" type="number" name="" id="" />
        </div>
        
        <div className="flex flex-col">
          <p className="text-sm pt-2.5">Blood Sugar Faster (md/dL)</p>
        </div>
        <div className="flex flex-col">
          <input className="rounded-md" type="number" name="" id="" />
        </div>
        <div className="flex flex-col">
        </div>
        <div className="flex flex-col"> 
        </div>
      </div>
      <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
    </div>
  );
}

export default AddAppointmentForm;
