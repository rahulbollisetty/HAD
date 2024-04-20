import { Button } from "@material-tailwind/react";
const PracticeEdit = () => {
  return (
    <div className="w-full">
      <div className="grid grid-cols-2  gap-5 text-[#7B7878] font-medium text-xl  p-5">
        <div className="flex flex-col">
          <p className="text-xl pb-2 font-medium">Clinic Name*</p>
          <input className="rounded-md w-full" type="text" />
        </div>
        <div className="flex flex-col">
          <p className="text-xl pb-2 font-medium">Specialization*</p>
          <select className="w-full rounded-md">
            <option></option>
          </select>
        </div>
      </div>
      <div className="grid grid-cols-1  gap-5 text-[#7B7878] font-medium text-xl  p-5">
        <div className="flex flex-col">
          <p className="text-xl pb-2 font-medium">Registraion Number*</p>
          <input className="rounded-md w-full" type="text" />
        </div>
      </div>
      <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl  p-5">
        <div className="flex flex-col">
          <p className="text-xl pb-2 font-medium">Contact Number</p>
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

        <div className="flex flex-col">
          <p className="text-xl pb-2 font-medium">GSTIN</p>
          <input className="rounded-md w-full" type="text" />
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
  );
};
export default PracticeEdit;
