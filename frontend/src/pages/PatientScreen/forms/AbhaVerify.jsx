import React, { useState } from "react";
import { useForm } from "react-hook-form";
import useFetchEventSource from "../../../hooks/useFetchEventSource";
import { toast } from 'react-toastify';
import useAuth from "../../../hooks/useAuth";
import { jwtDecode } from "jwt-decode";

function AbhaVerify({sendDataToParent}) {
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
    formState: { errors: errors2 },
  } = useForm();

  const eventSource = useFetchEventSource();

  const [txnId, setTxnId] = useState("");
  const {auth} = useAuth();
  const decoded = auth?.accessToken ? jwtDecode(auth.accessToken) : undefined;
  
  const sendData = (data) => {
    sendDataToParent(data); // Call the function to send data to parent
  };

  return (
    <div>
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
                {...register1("patientSBXId", {
                  required: "Required",
                  pattern: {
                    value: /^(.+)@sbx$/,
                    message: "ABHA Id should end with @sbx",
                  },
                })}
              />
              <button
                className="!absolute p-1 bg-[#006666] text-white right-1 top-[3px] rounded"
                onClick={handleSubmit1(async () => {
                  const data = {
                    patientSBXId: getValues1("patientSBXId"),
                    requesterId: decoded.hospitalId, // change this with jwt hospital Id
                    requesterType: "HIP",
                  };

                  try {
                    const abortController = new AbortController();
                    await eventSource("/patient/auth/userAuthInit", {
                      method: "POST",
                      body: JSON.stringify(data),
                      onmessage(response) {
                        var status = JSON.parse(response.data).statusCodeValue;
                        if (status >= 400)
                          toast.error(JSON.parse(response.data).body);
                        else if ((status == 200)) {
                          setTxnId(
                            JSON.parse(response.data).body.auth.transactionId
                          );
                          toast.success("Verification Initiated");
                        }
                        abortController.abort();
                      },
                      onclose(resp) {
                        // console.log(resp);
                        abortController.abort();
                      },
                      onerror(error) {
                        // console.log(error);
                        abortController.abort();
                        throw new Error(error);
                      },
                      signal: abortController.signal,
                    });
                  } catch (err) {
                    // console.log(err);
                  }
                })}
              >
                Confirm
              </button>
            </div>
            <p className="errorMsg">{errors1.patientSBXId?.message}</p>
          </div>
        </div>
      </div>
      <div className="grid grid-cols-4 gap-5 text-[#7B7878] font-medium	text-xl mt-8">
        <div>
          <div className="flex flex-col">
            <p className="mr-48 text-sm">Name</p>
            <div className="relative flex w-full">
              <input
                className="rounded-md w-full"
                type="text"
                name=""
                id=""
                {...register2("name", {
                  required: "Required",
                })}
              />
            </div>
            <p className="errorMsg">{errors2.name?.message}</p>
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="mr-38 text-sm">Date of Birth</p>
            <div className="relative flex w-full">
              <input
                className="rounded-md w-full"
                type="date"
                name=""
                id=""
                {...register2("dob", {
                  required: "Required",
                })}
              />
            </div>
            <p className="errorMsg">{errors2.dob?.message}</p>
          </div>
        </div>
        <div>
          <div className="flex flex-col">
            <p className="text-sm">Gender</p>
            <div className="flex gap-10">
              <select
                className="w-full rounded-md"
                name="gender"
                id=""
                {...register2("gender", { required: "Required" })}
              >
                <option hidden defaultValue={true}>Select Gender</option>
                <option value="M">Male</option>
                <option value="F">Female</option>
              </select>
            </div>
            <p className="errorMsg">{errors2.gender?.message}</p>
          </div>
        </div>
        <div>
          <div className="flex h-full items-end justify-center w-full items-center">
            <button
              type="submit"
              className="w-40 p-2 bg-[#006666] text-white rounded-md"
              onClick={handleSubmit2(async () => {
                const data = {
                  transactionId: txnId,
                  name: getValues2("name"),
                  gender: getValues2("gender"),
                  dob: getValues2("dob")                  
                };
                try {
                  const abortController = new AbortController();
                  await eventSource("/patient/auth/userAuthVerify", {
                    method: "POST",
                    body: JSON.stringify(data),
                    onmessage(response) {
                      var status = JSON.parse(response.data).statusCodeValue;
                      if (status >= 400)
                        toast.error(JSON.parse(response.data).body);
                      else if ((status = 200)) {
                        sendData(JSON.parse(response.data).body);
                        toast.success("Details Fetched!!");
                      }
                      abortController.abort();
                    },
                    onclose(resp) {
                      // console.log(resp);
                      abortController.abort();
                    },
                    onerror(error) {
                      // console.log(error);
                      abortController.abort();
                      throw new Error(error);
                    },
                    signal: abortController.signal,
                  });
                } catch (err) {
                  // console.log(err);
                  //   toast.error(err);
                }
              })}
            >
              Get Details
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AbhaVerify;
