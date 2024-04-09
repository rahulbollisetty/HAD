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
          <div className="m-s:max-h-[350px] lg:max-h-[420px] lg:s:max-h-[450px] 2xl:max-h-[470px] 3xl:max-h-[580px] 4xl:max-h-[850px] flex flex-col overflow-auto">
            <div className="">
              <p className="font-semibold ml-4 mt-4 text-lg">Vital Signs</p>

              <hr className="h-[2px] bg-[#7B7878] mt-2 mb-2 opacity-50	" />
              <div className="grid grid-cols-4 gap-5 text-[#7B7878] font-medium	text-xl mt-8">
                <div className="flex flex-col">
                  <p className="text-sm pt-2.5 px-6">Weight (Kg)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md"
                    type="number"
                    name="weight"
                    id=""
                  />
                </div>
                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Height (cms)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md mr-8"
                    type="number"
                    name="height"
                    id=""
                  />
                </div>

                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Age</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md"
                    type="number"
                    name="age"
                    id=""
                  />
                </div>
                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Temperature (Degree)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md mr-8"
                    type="number"
                    name="temperature"
                    id=""
                  />
                </div>

                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Blood Pressure (mmHg)</p>
                </div>
                <div className="flex flex-col">
                  <div className="flex">
                    <input
                      className="rounded-md"
                      type="number"
                      name="blood_pressure_systolic"
                      id=""
                      placeholder="Systolic"
                    />
                    <p className="text-3xl pt-1 pl-4 pr-5"> / </p>
                    <input
                      className="rounded-md"
                      type="number"
                      name="blood_pressure_distolic"
                      id=""
                      placeholder="Diastolic"
                    />
                  </div>
                </div>
                <div className="flex flex-col"></div>
                <div className="flex flex-col"></div>

                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Pulse (Heart beats/min)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md"
                    type="number"
                    name="pulse_rate"
                    id=""
                  />
                </div>
                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Resp. rate (beats/min)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md mr-8"
                    type="number"
                    name="respiration_rate"
                    id=""
                  />
                </div>

                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Total Cholesterol (mg/dL)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md"
                    type="number"
                    name="cholesterol"
                    id=""
                  />
                </div>
                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Triglycerides (mg/dL)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md mr-8"
                    type="number"
                    name="triglyceride"
                    id=""
                  />
                </div>

                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Blood Sugar Faster (md/dL)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md"
                    type="number"
                    name="blood_sugar"
                    id=""
                  />
                </div>
                <div className="flex flex-col"></div>
                <div className="flex flex-col"></div>
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
              <p className="font-semibold text-lg ml-4 my-4">Prescription</p>
              <hr className="mx-4 bg-[#7B7878]"></hr>
              <button
                className="border border-black my-5 mx-4 px-8 py-1.5 text-lg rounded-md"
                onClick={handleAddPrescription}
              >
                + Add
              </button>

              <div>
                {prescription.map((p, index) => (
                  <div key={index}>
                    <div className="flex flex-wrap ml-4 text-[#7B7878] font-medium	text-l w-full">
                      <div className="w-1/4 px-4">
                        <p>Drug</p>
                        <input
                          className="rounded-md w-72 mr-32 w-full"
                          type="text"
                          value={p.drug}
                          onChange={(e) => updateDrug(index, e)}
                        />
                      </div>
                      <div className=" w-1/4 px-4">
                        <p>Dosage</p>
                        <input
                          className="rounded-md w-72 mr-32 w-full"
                          type="number"
                          value={p.dosage}
                          onChange={(e) => updateDosage(index, e)}
                        />
                      </div>
                      <div className=" w-1/4 px-4">
                        <p>Frequency</p>
                        <input
                          className="rounded-md w-72 mr-32 w-full"
                          type="number"
                          value={p.frequency}
                          onChange={(e) => updateFrequency(index, e)}
                        />
                      </div>
                      <div className=" w-1/4 px-4">
                        <p>Duration</p>
                        <input
                          className="rounded-md w-72 mr-32 w-full"
                          type="number"
                          value={p.duration}
                          onChange={(e) => updateDuration(index, e)}
                        />
                      </div>
                    </div>
                    <div className="flex items-center text-md font-semibold text-[#7B7878] w-full">
                      <div className="rounded-md my-2 pl-8 mr-32 w-2/3">
                        <p>Instructions</p>
                        <input
                          className="my-2 rounded-md w-full"
                          type="text"
                          placeholder="Add Instructions"
                          value={p.instructions}
                          onChange={(e) => updateInstructions(index, e)}
                        />
                      </div>
                      <div className="bg-white ml-2 flex align-center justify-center">
                        <button
                          className="cursor-hover my-8"
                          onClick={() => handleRemovePrescription(index)}
                        >
                          <RiDeleteBinLine className="text-[#e90000] text-4xl" />
                        </button>
                      </div>
                    </div>
                    <br />
                    <hr className="bg-[#7B7878] mx-4" />
                  </div>
                ))}
              </div>

              <div className="flex text-[#7B7878] font-medium	text-l">
                <div className="flex-1 w-1/4 px-4">
                  <p className="pl-4 pr-40 mr-52">Drug</p>
                  <input
                    className="ml-4 w-full rounded-md"
                    type="text"
                    value={drug}
                    onChange={(e) => handleChangeDrug(e)}
                  />
                </div>
                <div className="flex-1 w-1/4 px-4">
                  <p>Dosage</p>
                  <input
                    className="rounded-md w-full mr-32"
                    type="number"
                    value={dosage}
                    onChange={(e) => handleChangeDosage(e)}
                  />
                </div>
                <div className="flex-1 w-1/4 px-4">
                  <p>Frequency</p>
                  <input
                    className="rounded-md w-full mr-32"
                    type="number"
                    value={frequency}
                    onChange={(e) => handleChangeFrequency(e)}
                  />
                </div>
                <div className="flex-1 w-1/4 px-4">
                  <p>Duration</p>
                  <input
                    className="rounded-md w-full"
                    type="number"
                    value={duration}
                    onChange={(e) => handleChangeDuration(e)}
                  />
                </div>
              </div>
              <input
                className="pl-4 my-6 ml-8 rounded-md w-2/3"
                type="text"
                placeholder="Add Instructions"
                value={instructions}
                onChange={(e) => handleChangeInstructions(e)}
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
