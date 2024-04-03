import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import encryptData from "../../../utils/encryptData";
import createHid from "../../../utils/createHid";
function AbhaRegister({ sendDataToParent }) {
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
    formState: { errors: errors1 },
  } = useForm();

  const {
    register: register2,
    handleSubmit: handleSubmit2,
    reset: reset2,
    getValues: getValues2,
    setValue: setValues2,
    formState: { errors: errors2 },
  } = useForm();

  const axiosPrivate = useAxiosPrivate();
  const [txn, setTxn] = useState("");
  const [isMobileLinked, setMobileLinked] = useState(false);
  const [showOtpInput, setShowOtpInput] = useState(false);
  const [showAbhaAddressSelect, setAbhaAddressshow] = useState(false);
  const [abhaNumber, setAbhaNumber] = useState("");
  const [abhaAddressList, setAbhaAddressList] = useState([]);
  const [profileData, setProfileData] = useState({});
  const [showMobileInput, setShowMobileInput] = useState(false);

  const createHealthId = async () => {
    try {
      const body = {
        txnId: txn,
      };
      const resp = await axiosPrivate.post(
        "http://127.0.0.1:9005/patient/createHealthId",
        body
      );
      toast.success("ABHA Account Created")
      try {
        const body = {
          authToken: resp.data.token,
        };
        const profileDetails = await axiosPrivate.post(
          "http://127.0.0.1:9005/patient/getProfileDetails",
          body
        );
        const month = profileDetails.data.monthOfBirth.padStart(2, "0");
        const day = profileDetails.data.dayOfBirth.padStart(2, "0");
        const dob = `${month}/${day}/${profileDetails.data.yearOfBirth}`;
        const modifiedData = {
          ...profileDetails.data,
          dob: dob,
          mobileNumber: getValues2("mobile"),
        };
        delete modifiedData.transactionId;
        setProfileData(modifiedData);
        setTxn(profileDetails.data.transactionId);
        try {
          const body = {
            transactionId: profileDetails.data.transactionId,
          };
          const abhaSuggestions = await axiosPrivate.post(
            "http://127.0.0.1:9005/patient/healthIdSuggestions",
            body
          );
          setAbhaAddressList(abhaSuggestions.data);
          setAbhaAddressshow(true);
          toast.info("Select the Abha Address");
        } catch (error) {
          console.log(error);
          toast.error("Unable to fetch Health Id seggestions");
        }
      } catch (error) {
        console.log(error);
      }
    } catch (error) {
      console.log(error);
      toast.error("Could Not Create Health Id, try again");
    }
  };

  return (
    <div className="grid grid-cols-2 gap-5 text-[#7B7878] font-medium	text-xl mt-8">
      <div>
        <div className="flex flex-col">
          <p className="mr-48 text-sm">Aadhaar Number</p>
          <div className="relative flex w-full">
            <input
              className="rounded-md pr-32 w-full"
              type="text"
              name=""
              {...register("aadhaar", {
                required: "Required",
                pattern: {
                  value: /^[0-9]+$/,
                  message: "Only Numbers are allowed",
                },
              })}
            />
            <button
              className="!absolute p-1 bg-[#006666] text-white right-1 top-[3px] rounded"
              onClick={handleSubmit(async () => {
                const data = {
                  aadhaar: encryptData(getValues("aadhaar")),
                };
                try {
                  const resp = await axiosPrivate.post(
                    "/patient/aadhaarOTPInit",
                    data
                  );
                  console.log(resp);
                  setTxn(resp.data.txnId);
                  setShowOtpInput(true);
                  setShowMobileInput(true);
                  toast.success("OTP sent to " + resp.data.mobileNumber);
                } catch (err) {
                  console.log(err);
                  if (err?.response?.data) {
                    if (err?.response?.data?.details)
                      toast.error(err.response.data.details[0].message);
                    else toast.error(err.response.data.message);
                  }
                }
              })}
            >
              Send OTP
            </button>
          </div>
          <p className="errorMsg">{errors.aadhaar?.message}</p>
        </div>
      </div>

      <div>
        <div className="flex flex-col">
          <p className="mr-48 text-sm">Mobile</p>
          <div className="relative flex w-full">
            <input
              className={`rounded-md pr-32 w-full ${
                showMobileInput
                  ? ""
                  : "bg-gray-200 text-gray-500 cursor-not-allowed"
              }`}
              disabled={!showMobileInput}
              type="text"
              name=""
              id=""
              {...register2("mobile", {
                required: "Required",
                pattern: {
                  value: /^[0-9]+$/,
                  message: "Only Numbers are allowed",
                },
              })}
            />
          </div>
          <p className="errorMsg">{errors2.mobile?.message}</p>
        </div>
      </div>
      <div>
        <div className="flex flex-col">
          <p className="mr-48 text-sm">OTP</p>
          <div className="relative flex w-full">
            <input
              className={`rounded-md w-full ${
                showOtpInput
                  ? ""
                  : "bg-gray-200 text-gray-500 cursor-not-allowed"
              }`}
              type="text"
              name=""
              disabled={!showOtpInput}
              id=""
              {...register2("otp", {
                required: "Required",
                pattern: {
                  value: /^[0-9]+$/,
                  message: "Only Numbers are allowed",
                },
              })}
            />
            <button
              type="submit"
              className={`!absolute p-1  text-white right-1 top-[3px] rounded ${
                showOtpInput ? "bg-[#006666]" : "bg-gray-500 cursor-not-allowed"
              }`}
              disabled={!showOtpInput}
              onClick={handleSubmit2(async () => {
                var data = null;
                var path = null;
                if (isMobileLinked) {
                  data = {
                    otp: encryptData(getValues2("otp")),
                    txnId: txn,
                  };
                  path = "/patient/mobileOTPVerify";
                } else {
                  data = {
                    otp: encryptData(getValues2("otp")),
                    transactionId: txn,
                  };
                  path = "/patient/aadharOTPVerify";
                }
                try {
                  const resp = await axiosPrivate.post(path, data);
                  console.log(resp);
                  if (isMobileLinked) {
                    setTxn(resp.data.txnId);
                    setMobileLinked(false);
                    setShowMobileInput(false);
                    reset2({ otp: "" });
                    setShowOtpInput(false);
                    toast.success(resp.data.message);
                    createHealthId();
                  } else {
                    const abhanumber = resp.data.healthIdNumber;
                    console.log(abhanumber);
                    setAbhaNumber(abhanumber.replace(/-/g, ""));
                    if (resp.data?.txnId) {
                      setTxn(resp.data.txnId);
                      try {
                        const data = {
                          mobile: getValues2("mobile"),
                          txnId: txn,
                        };
                        const resp = await axiosPrivate.post(
                          "/patient/checkAndMobileOTPInit",
                          data
                        );
                        console.log(resp);
                        setTxn(resp.data.txnId);
                        if (!resp.data?.mobileLinked) {
                          toast.info(
                            "Mobile Linked to Aadhaar is different, enter OTP sent to mobile"
                          );
                          setMobileLinked(true);
                          setShowMobileInput(true);
                          reset2({ otp: "" });
                        } else {
                          toast.success(
                            "OTP verified, Mobile Already linked to Aadhaar"
                          );
                          createHealthId();
                        }
                      } catch (error) {
                        console.log(error);
                      }
                    }
                  }
                } catch (err) {
                  console.log(err);
                }
              })}
            >
              Confirm
            </button>
          </div>
          <p className="errorMsg">{errors2.otp?.message}</p>
        </div>
      </div>
      <div>
        <div className="flex flex-col">
          <p className="mr-48 text-sm">ABHA Number</p>
          <div
            className="rounded-md pr-32 w-full bg-gray-200 min-h-[45px] cursor-not-allowed text-black"
            name=""
            disabled={!showOtpInput}
            id=""
          >
            <span className="inline-block align-middle p-2">{abhaNumber}</span>
          </div>
        </div>
      </div>
      <div>
        <div className="flex flex-col">
          <p className="text-sm">Select ID</p>
          <select
            disabled={!showAbhaAddressSelect}
            className={`w-full rounded-md ${
              showAbhaAddressSelect
                ? ""
                : "bg-gray-200 text-gray-500 cursor-not-allowed"
            }`}
            {...register1("phrAddress")}
          >
            <option value="" hidden defaultValue={true}>
              Select ID
            </option>
            {abhaAddressList.map((item, index) => (
              <option key={item} value={item}>
                {item}
              </option>
            ))}
          </select>
        </div>
      </div>
      <div>
        <div className="flex h-full items-end justify-center w-72">
          <button
            type="submit"
            disabled={!showAbhaAddressSelect}
            className={`w-40 p-2 text-white rounded-md ${
              showAbhaAddressSelect
                ? "bg-[#006666]"
                : "bg-gray-500 cursor-not-allowed"
            }`}
            onClick={handleSubmit1(async () => {
              const data = {
                phrAddress: getValues1("phrAddress"),
                transactionId: txn,
              };
              try {
                // const resp = await axiosPrivate.post("http://127.0.0.1:9005/patient/createPHRAddress", data);
                // console.log(resp);
                setProfileData({
                  ...profileData,
                  abhaNumber: abhaNumber,
                  abhaAddress: getValues1("phrAddress"),
                });
                // toast.success("ABHA Address created: " + resp.data.phrAdress);
                sendDataToParent(profileData);
              } catch (err) {
                console.log(err);
              }
            })}
          >
            Confirm
          </button>
        </div>
      </div>
    </div>
  );
}

export default AbhaRegister;
