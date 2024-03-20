import React from "react";
import { useForm } from "react-hook-form";
import { Radio } from "@material-tailwind/react";

function AddPatientForm() {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();

  return (
    <div className="2xl:max-h-[43rem] 4xl:max-h-[70rem] overflow-auto p-4">
      <div className="grid grid-cols-2 gap-5 text-[#7B7878] font-medium	text-xl mt-8">
        <div>
          <div className="flex flex-col">
            <p className="mr-48 text-sm">ABHA ID</p>
            <div className="relative flex w-full">
              <input
                className="rounded-md pr-32 w-full"
                type="text"
                name=""
                id=""
              />
              <button className="!absolute p-1 bg-[#006666] text-white right-1 top-[3px] rounded">
                Send OTP
              </button>
            </div>
          </div>
        </div>

        <div>
          <div className="flex flex-col">
            <p className="mr-48 text-sm">ABHA Number</p>
            <input className="rounded-md w-full" type="text" name="" id="" />
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="mr-48 text-sm">OTP</p>
            <input className="rounded-md w-72" type="text" name="" id="" />
          </div>
        </div>
        <div>
          <div className="flex h-full items-end justify-center w-72">
            <button className="w-40 p-2 bg-[#006666] text-white rounded-md">
              Get Details
            </button>
          </div>
        </div>
      </div>
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
                <option disabled >
                  Select Gender
                </option>
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
              <option value="" disabled >
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
      <div className="grid grid-cols-2 gap-5 text-[#7B7878] font-medium	text-xl mt-8">
        <div>
          <div className="flex flex-col">
            <p className="mr-48 text-sm">ABHA ID</p>
            <div className="relative flex w-full">
              <input
                className="rounded-md pr-32 w-full"
                type="text"
                name=""
                id=""
              />
              <button className="!absolute p-1 bg-[#006666] text-white right-1 top-[3px] rounded">
                Send OTP
              </button>
            </div>
          </div>
        </div>

        <div>
          <div className="flex flex-col">
            <p className="mr-48 text-sm">Mobile</p>
            <div className="relative flex w-full">
              <input
                className="rounded-md pr-32 w-full"
                type="text"
                name=""
                id=""
              />
              <button className="!absolute p-1 bg-[#006666] text-white right-1 top-[3px] rounded">
                Send OTP
              </button>
            </div>
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="mr-48 text-sm">OTP</p>
            <input className="rounded-md w-72" type="text" name="" id="" />
          </div>
        </div>
        <div>
        <div className="flex flex-col">
            <p className="text-sm">Select ID</p>
            <select className="w-full rounded-md" name="bloodGroup" id="">
              <option value="" disabled >
                Select ID
              </option>
            </select>
          </div>
          
        </div>
      </div>
      
    </div>
  );
}

export default AddPatientForm;
