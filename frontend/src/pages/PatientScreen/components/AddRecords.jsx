import { useState, useEffect } from "react";
import { RiDeleteBinLine } from "react-icons/ri";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";

function AddRecords({ patientId, appointment_id, sendDataToParent, status }) {
  const axiosPrivate = useAxiosPrivate();
  useEffect(() => {
    if (!appointment_id)
      sendDataToParent("Select an appointment to view records");
    console.log("patient", patientId.patientId, "app", appointment_id);
    try {
      const fetchData = async () => {
        const path = `http://127.0.0.1:9005/patient/appointment/getPatientVitals?id=${appointment_id}`;
        const resp = await axiosPrivate.get(path);
        console.log(resp.data, "hello");
        setVitals(resp.data);
      };
      fetchData();
    } catch (error) {
      console.log(error);
    }

    // try {
    //   const getAppointmentDetails = async () => {
    //     const path = `http://127.0.0.1:9005/patient/appointment/getAppointmentDetails?id=${appointment_id}`;
    //     const resp = await axiosPrivate.get(path);
    //     console.log(resp.data, "appointment details");
    //     console.log(resp.data[0].status, "Fetching Status");
    //     if (resp.data[0].status === "Completed") {
    //       getPrescription();
    //       setStatus(true);
    //     } else {
    //       console.error("Status data is undefined in response.");
    //     }
    //   };
    //   getAppointmentDetails();
    // } catch (error) {
    //   console.log(error);
    // }

    const getPrescription = async () => {
      const path = `http://127.0.0.1:9005/patient/appointment/getPrescription`;
      const requestBody = {
        appointment_id: appointment_id,
      };
      const resp = await axiosPrivate.post(path, requestBody);
      console.log(resp.data, "Prescription data");
      setPrescription(resp.data);
    };
    getPrescription();
  }, []);

  const [Status, setStatus] = useState(false);
  const [prescription, setPrescription] = useState([]);
  const [drug, setDrug] = useState("");
  const [dosage, setDosage] = useState(0);
  const [frequency, setFrequency] = useState(0);
  const [duration, setDuration] = useState(0);
  const [instructions, setInstructions] = useState("");
  const [Weight, setWeight] = useState(0);
  const [Age, setAge] = useState(0);
  const [Systolic, setSystolic] = useState(0);
  const [Diastolic, setDiastolic] = useState(0);
  const [Pulse, setPulse] = useState(0);
  const [Cholestrol, setCholestrol] = useState(0);
  const [Sugar, setSugar] = useState(0);
  const [Height, setHeight] = useState(0);
  const [Temperature, setTemperature] = useState(0);
  const [RespirationRate, setRespirationRate] = useState(0);
  const [Triglycerides, setTriglycerides] = useState(0);
  const [observations, setobservations] = useState("");
  console.log(status, "Hello");
  function handleAddPrescription() {
    if (
      drug !== "" &&
      dosage !== 0 &&
      duration !== 0 &&
      frequency !== 0 &&
      instructions !== ""
    ) {
      const newPrescription = {
        drug: drug,
        dosage: dosage,
        duration: duration,
        frequency: frequency,
        instructions: instructions,
      };

      setPrescription((prescription) => [...prescription, newPrescription]);
    }
    setDosage(0);
    setDrug("");
    setDuration(0);
    setFrequency(0);
    setInstructions("");
  }

  const linkCareContext = () => {
    try {
      const completeAppointment = async () => {
        const path = `http://127.0.0.1:9005/patient/appointment/completeAppointment`;
        const requestBody = {
          opId: appointment_id,
          prescription: prescription,
          patientId: parseInt(patientId.patientId),
          observations: observations,
        };
        console.log(requestBody);
        const resp = await axiosPrivate.post(path, requestBody);
        console.log(resp.data, "Care Context Response");
      };
      completeAppointment();
    } catch (error) {
      console.log(error);
    }
  };

  const handleRemovePrescription = (index) => {
    const updatedPrescriptions = prescription.filter((_, i) => i !== index);
    setPrescription(updatedPrescriptions);
  };

  function handleChangeDrug(event) {
    setDrug(event.target.value);
  }

  function handleChangeDosage(event) {
    setDosage(event.target.value);
  }

  function handleChangeFrequency(event) {
    setFrequency(event.target.value);
  }

  function handleChangeDuration(event) {
    setDuration(event.target.value);
  }

  function handleChangeInstructions(event) {
    setInstructions(event.target.value);
  }

  const updateDrug = (index, e) => {
    const updatedPrescription = [...prescription];
    updatedPrescription[index].drug = e.target.value;
    setPrescription(updatedPrescription);
  };

  const updateDosage = (index, e) => {
    const updatedPrescription = [...prescription];
    updatedPrescription[index].dosage = e.target.value;
    setPrescription(updatedPrescription);
  };

  const updateDuration = (index, e) => {
    const updatedPrescription = [...prescription];
    updatedPrescription[index].duration = e.target.value;
    setPrescription(updatedPrescription);
  };

  const updateFrequency = (index, e) => {
    const updatedPrescription = [...prescription];
    updatedPrescription[index].frequency = e.target.value;
    setPrescription(updatedPrescription);
  };

  const updateInstructions = (index, e) => {
    const updatedPrescription = [...prescription];
    updatedPrescription[index].instructions = e.target.value;
    setPrescription(updatedPrescription);
  };

  const changeObservations = (e) => {
    setobservations(e.target.value);
  };

  const setVitals = (data) => {
    setWeight(data.weight);
    setCholestrol(data.cholesterol);
    setDiastolic(data.blood_pressure_distolic);
    setAge(data.age);
    setHeight(data.height);
    setPulse(data.pulse_rate);
    setSystolic(data.blood_pressure_systolic);
    setSugar(data.blood_sugar);
    setTemperature(data.temperature);
    setTriglycerides(data.triglyceride);
    setRespirationRate(data.respiration_rate);
  };

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
                    className="rounded-md text-gray-500 cursor-not-allowed bg-gray-200"
                    disabled={true}
                    type="number"
                    name="weight"
                    value={Weight}
                  />
                </div>
                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Height (cms)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md mr-8 text-gray-500 cursor-not-allowed bg-gray-200"
                    disabled={true}
                    type="number"
                    name="height"
                    value={Height}
                  />
                </div>

                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Age</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md text-gray-500 cursor-not-allowed bg-gray-200"
                    disabled={true}
                    type="number"
                    name="age"
                    value={Age}
                  />
                </div>
                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Temperature (Degree)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md mr-8 text-gray-500 cursor-not-allowed bg-gray-200"
                    disabled={true}
                    type="number"
                    name="temperature"
                    value={Temperature}
                  />
                </div>

                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Blood Pressure (mmHg)</p>
                </div>
                <div className="flex flex-col">
                  <div className="flex">
                    <input
                      className="rounded-md text-gray-500 cursor-not-allowed bg-gray-200"
                      disabled={true}
                      type="number"
                      name="blood_pressure_systolic"
                      value={Diastolic}
                      placeholder="Systolic"
                    />
                    <p className="text-3xl pt-1 pl-4 pr-5"> / </p>
                    <input
                      className="rounded-md text-gray-500 cursor-not-allowed bg-gray-200"
                      disabled={true}
                      type="number"
                      name="blood_pressure_distolic"
                      value={Systolic}
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
                    className="rounded-md text-gray-500 cursor-not-allowed bg-gray-200"
                    disabled={true}
                    type="number"
                    name="pulse_rate"
                    value={Pulse}
                  />
                </div>
                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Resp. rate (beats/min)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md mr-8 text-gray-500 cursor-not-allowed bg-gray-200"
                    disabled={true}
                    type="number"
                    name="respiration_rate"
                    value={RespirationRate}
                  />
                </div>

                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Total Cholesterol (mg/dL)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md text-gray-500 cursor-not-allowed bg-gray-200"
                    disabled={true}
                    type="number"
                    name="cholesterol"
                    value={Cholestrol}
                  />
                </div>
                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Triglycerides (mg/dL)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md mr-8 text-gray-500 cursor-not-allowed bg-gray-200"
                    disabled={true}
                    type="number"
                    name="triglyceride"
                    value={Triglycerides}
                  />
                </div>

                <div className="flex flex-col px-6">
                  <p className="text-sm pt-2.5">Blood Sugar Faster (md/dL)</p>
                </div>
                <div className="flex flex-col">
                  <input
                    className="rounded-md text-gray-500 cursor-not-allowed bg-gray-200"
                    disabled={true}
                    type="number"
                    name="blood_sugar"
                    value={Sugar}
                  />
                </div>
                <div className="flex flex-col"></div>
                <div className="flex flex-col"></div>
              </div>
              <hr className="h-0 bg-[#7B7878] mx-6 mt-6" />

              <hr className="bg-[#7B7878]" />
              <p className="font-semibold text-lg ml-4 my-4">Prescription</p>
              <hr className="mx-4 bg-[#7B7878]"></hr>

              <div>
                {prescription.map((p, index) => (
                  <div key={index}>
                    <div className="flex flex-wrap ml-4 text-[#7B7878] font-medium text-l w-full">
                      <div className="w-1/4 px-4">
                        <p>Drug</p>
                        <input
                          className="rounded-md w-72 mr-32 w-full bg-gray-200 text-gray-500 cursor-not-allowed"
                          disabled={true}
                          type="text"
                          value={p.drug}
                          onChange={(e) => updateDrug(index, e)}
                        />
                      </div>
                      <div className=" w-1/4 px-4">
                        <p>Dosage</p>
                        <input
                          className="rounded-md w-72 mr-32 w-full text-gray-500 cursor-not-allowed bg-gray-200"
                          disabled={true}
                          type="number"
                          value={p.dosage}
                          onChange={(e) => updateDosage(index, e)}
                        />
                      </div>
                      <div className=" w-1/4 px-4">
                        <p>Frequency</p>
                        <input
                          className="rounded-md w-72 mr-32 w-full text-gray-500 cursor-not-allowed bg-gray-200"
                          disabled={true}
                          type="number"
                          value={p.frequency}
                          onChange={(e) => updateFrequency(index, e)}
                        />
                      </div>
                      <div className=" w-1/4 px-4">
                        <p>Duration</p>
                        <input
                          className="rounded-md w-72 w-full text-gray-500 cursor-not-allowed bg-gray-200"
                          disabled={true}
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
                          className="my-2 rounded-md w-full text-gray-500 cursor-not-allowed bg-gray-200"
                          disabled={true}
                          type="text"
                          placeholder="Add Instructions"
                          value={p.instructions}
                          onChange={(e) => updateInstructions(index, e)}
                        />
                      </div>
                      <div>
                        {!status ? (
                          <div className="bg-white ml-2 flex align-center justify-center">
                            <button
                              className="cursor-hover my-8"
                              onClick={() => handleRemovePrescription(index)}
                            >
                              <RiDeleteBinLine className="text-[#e90000] text-4xl" />
                            </button>
                          </div>
                        ) : (
                          <></>
                        )}
                      </div>
                    </div>
                    <br />
                    <hr className="bg-[#7B7878] mx-4" />
                  </div>
                ))}
              </div>

              <div>
                {!status ? (
                  <>
                    <button
                      className="border border-black my-5 mx-4 px-8 py-1.5 text-lg rounded-md"
                      onClick={handleAddPrescription}
                    >
                      + Add
                    </button>
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
                          className="ml-4 w-full rounded-md"
                          type="number"
                          value={dosage}
                          onChange={(e) => handleChangeDosage(e)}
                        />
                      </div>
                      <div className="flex-1 w-1/4 px-4">
                        <p>Frequency</p>
                        <input
                          className="ml-4 w-full rounded-md"
                          type="number"
                          value={frequency}
                          onChange={(e) => handleChangeFrequency(e)}
                        />
                      </div>
                      <div className="flex-1 w-1/4 px-4">
                        <p>Duration</p>
                        <input
                          className="ml-4 w-full rounded-md "
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
                    <p className="font-semibold text-lg ml-4 my-4">
                      Add Observations
                    </p>
                    <input
                      type="text"
                      className="pl-4 my-6 ml-8 rounded-md w-2/3"
                      placeholder="Add Observations"
                      value={observations}
                      onChange={(e) => changeObservations(e)}
                    />
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
                        <button
                          className="bg-[#FFA000] text-white ml-4 w-32 h-10 mr-6 border border-[#a18042] rounded-md"
                          onClick={() => linkCareContext()}
                        >
                          Save
                        </button>
                      </div>
                    </div>
                  </>
                ) : (
                  <></>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
export default AddRecords;
