import { useEffect, useState } from "react";
import DoctorEdit from "./DoctorEdit";
import { Button } from "@material-tailwind/react";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import { useForm } from "react-hook-form";

const StaffEdit = () => {
  const {
    register,
    handleSubmit,
    reset,
    getValues,
    setValue,
    formState: { errors },
  } = useForm();
  const axiosPrivate = useAxiosPrivate();
  const [userDetails, setUserDetails] = useState({});
  const [states, setStates] = useState([]);
  const [role, setRole] = useState("");
  const [username, setUsername] = useState("");
  const [district, setdistrict] = useState([]);
  useEffect(() => {
    const username = localStorage.getItem("username");
    setUsername(username);
    const role = localStorage.getItem("role");
    setRole(localStorage.getItem("role"));
    const requestBody = {
      username: username,
    };
    const getUserDetails = async () => {
      try {
        const response = await axiosPrivate.post(
          `http://127.0.0.1:9005/auth/get-${role.toLocaleLowerCase()}-details-by-username`,
          requestBody
        );
        console.log(response.data);
        setUserDetails(response.data);
      } catch (err) {
        if (!err?.response) {
          console.error("No Server Response");
        }
      }
    };
    getUserDetails();

    const getStates = async () => {
      try {
        const response = await axiosPrivate.post(
          "http://127.0.0.1:9005/patient/getLgdStatesList"
        );
        setStates(response.data);
        console.log(response.data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    console.log(getValues());

    getStates();
  }, []);

  const handleDistrict = (event) => {
    console.log(event);
    const selectedState = states[event].districts;
    console.log(selectedState);
    setdistrict(selectedState);
    console.log(states);
    setValue("state", `${states[event].name}-${states[event].code}`);
  };

  const onSubmit = async () => {
    setValue("role", role.toLowerCase());
    setValue("username", username);
    console.log(getValues());
    try {
      const response = await axiosPrivate.post(
        `http://127.0.0.1:9005/auth/editDetails`,
        getValues()
      );
      console.log(response.data);
      setUserDetails(response.data);
    } catch (err) {
      if (!err?.response) {
        console.error("No Server Response");
      }
    }
  };

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
                  <p className="ml-6 text-black font-medium">
                    {userDetails.first_Name} {userDetails.last_Name}
                  </p>
                </span>
              </div>
              <div className="flex-1">
                <span className="font-semibold flex mr-20 text-[#7B7878]">
                  Registration Number*
                  <p className="ml-6 text-black font-medium">
                    {userDetails.registration_number}
                  </p>
                </span>
              </div>
              <div className="flex-1">
                <span className="font-semibold flex ml-auto mr-20 text-[#7B7878]">
                  Date of Birth
                  <p className="ml-6 text-black font-medium">
                    {userDetails.dob}
                  </p>
                </span>
              </div>
            </div>
          </div>
          <div className="w-full">
            <div className="flex mt-2 ml-8 text-l">
              <div className="flex-1">
                <span className="font-semibold flex ml-auto mr-20 text-[#7B7878]">
                  Gender
                  <p className="ml-6 text-black font-medium">
                    {userDetails.gender}
                  </p>
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
          <form onSubmit={handleSubmit(onSubmit)}>
            <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl  p-5">
              <div className="flex flex-col">
                <p className="text-xl pb-2 font-medium">Mobile Number</p>
                <input
                  className="rounded-md w-full"
                  type="text"
                  {...register("mobileNumber", { required: true })}
                />
              </div>
              {/* <div className="flex flex-col">
                <p className="text-xl pb-2 font-medium">Email Address</p>
                <input
                  className="rounded-md w-full"
                  type="text"
                  {...register("email", { required: true })}
                />
              </div> */}
              <div className="flex flex-col"></div>
            </div>
            <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
            <div className="grid grid-cols-3  gap-5 text-[#7B7878] font-medium text-xl  p-5">
              <div className="flex flex-col">
                <p className="text-xl pb-2 font-medium">Address Line*</p>
                <input
                  className="rounded-md w-full"
                  type="text"
                  {...register("addressLine", { required: true })}
                />
              </div>
              <div className="flex-1">
                <p className="mb-2">State*</p>
                <select
                  className="w-full rounded-md"
                  name="doctor"
                  onChange={(e) => handleDistrict(e.target.value)}
                  defaultValue="" // Set the default value to an empty string
                >
                  <option disabled value="">
                    Select State
                  </option>
                  {states.map((state, index) => (
                    <option
                      key={index}
                      className="font-light text-s option-hover"
                      value={index}
                    >
                      {state.name}
                    </option>
                  ))}
                </select>
              </div>
              <div className="flex-1">
                <p className="mb-2">District*</p>
                <select
                  className="w-full rounded-md"
                  name="district"
                  id=""
                  {...register("district", {
                    required: "Required",
                  })}
                >
                  <option value="" hidden defaultValue={true}>
                    Select District
                  </option>
                  {district.map((item, index) => (
                    <option key={item.code} value={`${item.name}-${item.code}`}>
                      {item.name}
                    </option>
                  ))}
                </select>
              </div>
            </div>

            <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
            <div className="flex flex-col text-[#7B7878] px-5">
              <p className="text-xl pt-2 font-medium">Pincode</p>
              <input
                className="rounded-md w-1/4"
                type="text"
                {...register("pincode", {
                  required: "Required",
                })}
              />
            </div>
            <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
            <div className="flex flex-row-reverse  gap-5 text-[#7B7878] font-medium text-xl mr-10 p-5">
              <Button variant="filled" className="bg-[#FFA000]" type="submit">
                <span>Save</span>
              </Button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};
export default StaffEdit;
