import Sidebar from "../../Sidebar";
import Profile from "./Profile";
import DoctorTab from "./Tabs";
import { useEffect } from "react";
function AddRecords({appointment_id, sendDataToParent}) {
  useEffect(() => {
    if(!appointment_id)sendDataToParent("Select an appointment to view records")
  }, []);
  return (
    <div className="flex flex-col">
      <div className="">
        <div className="border mx-3 my-4 border-[#006666] rounded-md border-l-4">
          <div className=" m-s:max-h-[350px] lg:max-h-[420px] lg-s:max-h-[450px] 2xl:max-h-[470px] 3xl:max-h-[580px] 4xl:max-h-[850px] flex flex-col overflow-auto">
            <div className="">
              <p className="font-semibold ml-4 mt-4 text-lg">Vital Signs</p>
              <div className="text-[#7B7878] font-semibold mt-8">
                <div className="flex mb-6">
                  <div className="pl-8 flex items-center">
                    <p className="mr-48 text-sm">Weight(kg)</p>
                    <input
                      className="rounded-md w-80"
                      type="number"
                      name=""
                      id=""
                    />
                  </div>
                  <div className="pr-8 ml-auto flex items-center">
                    <p className="ml-auto">Blood Pressure(mm/Hg)</p>
                    <input
                      className="rounded-md ml-20 w-80"
                      type="number"
                      name=""
                      id=""
                    />
                  </div>
                </div>

                <div className="flex items-center mb-6">
                  <div className="pl-8 flex  items-center">
                    <p className="mr-10">Pulse rate(Heart Beats / min)</p>
                    <input
                      className="ml-1 w-80 rounded-md"
                      type="number"
                      name=""
                      id=""
                    />
                  </div>
                  <div className="flex ml-auto items-center">
                    <p className="mr-20">Temperature(Degree C)</p>
                    <input
                      className="ml-auto w-80 mr-8 rounded-md"
                      type="number"
                      name=""
                      id=""
                    />
                  </div>
                </div>

                <div className="flex items-center pl-8">
                  <p className="mr-20">Resp. Rate(Beats / min)</p>
                  <input
                    className="ml-1 w-80 rounded-md"
                    type="number"
                    name=""
                    id=""
                  />
                </div>
              </div>
              <hr className="h-0 bg-[#7B7878] mx-6 mt-6" />
              <div className="flex ">
                <div className="ml-auto my-6">
                  <button className="w-32 h-10  border border-black rounded-md">
                    Cancel
                  </button>
                  <button className="bg-[#FFA000] text-white ml-4 w-32 h-10 mr-6 border border-[#a18042] rounded-md">
                    Edit
                  </button>
                </div>
              </div>
              <hr className="bg-[#7B7878]" />
              <p className="font-semibold text-lg ml-4 my-4">Prescriptions</p>
              <hr className="mx-4 bg-[#7B7878]"></hr>
              <button className="border border-black my-5 mx-4 px-8 py-1.5 text-lg rounded-md">
                + Add
              </button>
              <div className="flex text-md font-semibold text-[#7B7878]">
                <div className="flex-1">
                  <p className="pl-4 pr-40 mr-52">Drug</p>
                  <input
                    className="ml-4 w-72 mr-32 rounded-md"
                    type="text"
                    name=""
                    id=""
                  />
                </div>
                <div className="flex-1">
                  <p className="mr-52 pl-4 pr-36">Dosage</p>
                  <input
                    className="rounded-md  w-72 mr-32"
                    type="text"
                    name=""
                    id=""
                  />
                </div>
                <div className="flex-1">
                  <p className="mr-60 pr-24">Frequency</p>
                  <input
                    className="rounded-md  w-72 mr-32"
                    type="text"
                    name=""
                    id=""
                  />
                </div>
                <div className="flex-1">
                  <p className="">Duration</p>
                  <input
                    className="rounded-md w-72"
                    type="text"
                    name=""
                    id=""
                  />
                </div>
              </div>
              <input
                className="pl-4 my-6 ml-4 rounded-md w-2/3"
                type="text"
                placeholder="Add Instructions"
              />
              <hr className="bg-[#7B7878] mx-4" />
              <div className="flex">
                <div className="ml-auto my-4">
                  <button className="w-32 h-10  border border-black rounded-md">
                    Cancel
                  </button>
                  <button className="bg-[#FFA000] text-white ml-4 w-32 h-10 mr-6 border border-[#a18042] rounded-md">
                    Save
                  </button>
                </div>
              </div>
              <hr className="bg-[#7B7878] mx-4" />
              <p className="font-semibold text-lg ml-4 my-4">Add Files</p>

              <div className="flex mx-4 my-6">
                <label
                  htmlFor="imageInput"
                  className="block bg-blue-500 border border-white text-white px-4 py-2 rounded-md cursor-pointer"
                >
                  Upload image
                </label>
                <input
                  id="imageInput"
                  className="hidden"
                  type="file"
                  accept="image/*"
                />
                <label
                  htmlFor="fileInput"
                  className="bg-white border border-black mx-12 px-4 py-2 w-40 items-center justify-center flex rounded-md cursor-pointer"
                >
                  Upload File
                </label>
                <input id="fileInput" className="hidden" type="file" />
              </div>

              <input
                className="ml-4 pl-4 rounded-md w-2/3"
                type="text"
                placeholder="Add Instructions"
              />
              <hr className="bg-[#7B7878] mx-4 my-6" />
              <div className="flex">
                <div className="ml-auto my-4">
                  <button className="w-32 h-10  border border-black rounded-md">
                    Cancel
                  </button>
                  <button className="bg-[#FFA000] text-white ml-4 w-32 h-10 mr-6 border border-[#a18042] rounded-md">
                    Save
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
export default AddRecords;
