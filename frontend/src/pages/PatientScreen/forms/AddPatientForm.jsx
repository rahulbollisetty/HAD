import React, { useState } from "react";
import { useForm } from "react-hook-form";
import AbhaRegister from "./AbhaRegister";
import AbhaVerify from "./AbhaVerify";

function AddPatientForm() {
  const {
    register,
    handleSubmit,
    reset,
    getValues,
    formState: { errors },
  } = useForm();

  const {
    register: register1,
    handleSubmit: handleSubmit1,
    reset: reset1,
    getValues: getValues1,
    formState: { errors: error1 },
  } = useForm();

  const {
    register: register2,
    handleSubmit: handleSubmit2,
    reset: reset2,
    getValues: getValues2,
    formState: { errors: errors2 },
  } = useForm();

  return (
    <div className="2xl:max-h-[43rem] 4xl:max-h-[70rem] overflow-auto p-4">
      <AbhaVerify />
      <hr className="h-[2px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
      <div className="grid grid-cols-3 gap-5 text-[#7B7878] font-medium	text-xl mt-8">
        <div>
          <div className="flex flex-col">
            <p className="text-sm">Patient Name</p>
            <input className="rounded-md" type="text" name="" id="" />
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm">Mobile Number</p>
            <input className="rounded-md" type="text" name="" id="" />
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm">Email</p>
            <input className="rounded-md" type="email" name="" id="" />
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm">Gender</p>
            <div className="flex gap-10">
              <select className="w-full rounded-md" name="gender" id="">
                <option disabled>Select Gender</option>
                <option value="male">Male</option>
                <option value="female">Female</option>
              </select>
            </div>
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm">DOB</p>
            <input className="rounded-md" type="date" name="" id="" />
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm">Blood Group</p>
            <select className="w-full rounded-md" name="bloodGroup" id="">
              <option value="" disabled>
                Select Blood Group
              </option>
              <option value="A+">A+</option>
              <option value="A-">A-</option>
              <option value="B+">B+</option>
              <option value="B-">B-</option>
              <option value="AB+">AB+</option>
              <option value="AB-">AB-</option>
              <option value="O+">O+</option>
              <option value="O-">O-</option>
            </select>
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm">Occupation</p>
            <input className="rounded-md" type="text" name="" id="" />
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm">Family Member Name</p>
            <input className="rounded-md" type="text" name="" id="" />
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm">Relationship</p>
            <input className="rounded-md" type="text" name="" id="" />
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm">Address</p>
            <input className="rounded-md" type="text" name="" id="" />
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm">Town/City</p>
            <input className="rounded-md" type="text" name="" id="" />
          </div>
        </div>

        <div>
          <div className="flex flex-col">
            <p className="text-sm">Pincode</p>
            <input className="rounded-md" type="text" name="" id="" />
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm">State</p>
            <select className="w-full rounded-md" name="bloodGroup" id="">
              <option value="" disabled>
                Select State
              </option>
            </select>
          </div>
        </div>
      </div>
      <hr className="h-[2px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
      <div>
        <p className="font-semibold text-xl ml-4 mt-4 mb-4 text-[#444444]">
          Creating ABHA ID
        </p>
      </div>
      <AbhaRegister />
    </div>
  );
}

export default AddPatientForm;
