import DoctorEdit from "./DoctorEdit";
import { Button } from "@material-tailwind/react";

const StaffEdit = () => {
  return (
    <div className="w-full flex flex-col">
      <div className="w-full flex flex-row">
        <div className="text-[#02685A] text-3xl font-bold p-0 ml-10 w-1/5 flex flex-col">
          <p>Edit Details</p>
        </div>
      </div>
      <div className="w-full">
        <div className="flex flex-col m-5 gap-2">
          <div className="w-full">
            <div className="flex mt-2 ml-8 text-l">
              <div className="flex-1">
                <span className="font-semibold flex ml-auto mr-20 text-[#7B7878]">
                  Name*
                  <p className="ml-6 text-black font-medium">B. Rahul</p>
                </span>
              </div>
              <div className="flex-1">
                <span className="font-semibold flex mr-20 text-[#7B7878]">
                  Registration Number*
                  <p className="ml-6 text-black font-medium">123456789</p>
                </span>
              </div>
              <div className="flex-1">
                <span className="font-semibold flex ml-auto mr-20 text-[#7B7878]">
                  Date of Birth(DD/MM/YYYY)
                  <p className="ml-6 text-black font-medium">06/09/1969</p>
                </span>
              </div>
            </div>
          </div>
          <div className="w-full">
            <div className="flex mt-2 ml-8 text-l">
              <div className="flex-1">
                <span className="font-semibold flex ml-auto mr-20 text-[#7B7878]">
                  Gender
                  <p className="ml-6 text-black font-medium">Male</p>
                </span>
              </div>
              <div className="flex-1">
                <span className="font-semibold flex mr-20 text-[#7B7878]">
                  Qualification
                  <p className="ml-6 text-black font-medium">MBBS</p>
                </span>
              </div>
              <div className="flex-1"></div>
            </div>
          </div>
          <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
          <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl  p-5">
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">Mobile Number</p>
              <input className="rounded-md w-full" type="text" />
            </div>
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">Email Address</p>
              <input className="rounded-md w-full" type="text" />
            </div>
            <div className="flex flex-col"></div>
          </div>
          <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
          <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl  p-5">
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">Address Line*</p>
              <input className="rounded-md w-full" type="text" />
            </div>
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">Town/City</p>
              <input className="rounded-md w-full" type="text" />
            </div>
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">Pincode</p>
              <input className="rounded-md w-full" type="text" />
            </div>
          </div>
          <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl  p-5">
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">State</p>
              <select className="w-full rounded-md">
                <option></option>
              </select>
            </div>
            <div className="flex flex-col"></div>
            <div className="flex flex-col"></div>
          </div>
          <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
          <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl  p-5">
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">Username</p>
              <input className="rounded-md w-full" type="text" />
            </div>
            <div className="flex flex-col">
              <p className="text-xl pb-2 font-medium">Password</p>
              <input className="rounded-md w-full" type="password" />
            </div>
            <div className="flex flex-col"></div>
          </div>
          <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
          <div className="flex flex-row-reverse  gap-5 text-[#7B7878] font-medium text-xl mr-10 p-5">
            <Button variant="filled" className="bg-[#FFA000]">
              <span>Save</span>
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};
export default StaffEdit;
