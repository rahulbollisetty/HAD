import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { fetchEventSource } from "@microsoft/fetch-event-source";
import useFetchEventSource from "../../../hooks/useFetchEventSource";
import toast from "react-hot-toast";
import dayjs from "dayjs";

function AbhaVerify() {
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

  const [showOtpInput, setShowOtpInput] = useState(false);
  const [txnId, setTxnId] = useState("");

  return (
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
                console.log("jlkjlkj;lkj");
                const data = {
                  patientSBXId: getValues1("patientSBXId"),
                  requesterId: "IN2210000259",
                  requesterType: "HIP",
                };

                try {
                  const abortController = new AbortController();
                  await eventSource(
                    "/patient/auth/userAuthInit",
                    {
                      method: "POST",
                      body: JSON.stringify(data),
                      onmessage(response) {
                        console.log("response ", response);
                        var status = JSON.parse(response.data).statusCodeValue;
                        if (status >= 400)
                          toast.error(JSON.parse(response.data).body);
                        else if ((status = 200)) {
                          setShowOtpInput(true);
                          setTxnId(JSON.parse(response.data).body.auth.transactionId);
                          toast.success("OTP Sent");
                        }
                        abortController.abort();
                      },
                      onclose(resp) {
                        console.log(resp);
                        abortController.abort();
                      },
                      onerror(error) {
                        console.log(error);
                        abortController.abort();
                        throw new Error(error);
                      },
                      signal: abortController.signal,
                    }
                  );
                } catch (err) {
                  console.log(err);
                  //   toast.error(err);
                }
              })}
            >
              Confirm
            </button>
          </div>
          <p className="errorMsg">{errors1.patientSBXId?.message}</p>
        </div>
      </div>
      <div>
        <div className="flex flex-col">
          <p className="mr-48 text-sm">OTP</p>
          <div className="relative flex w-full">
            <input
              className={`rounded-md pr-32 w-full ${
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
                  value: /^(.+)@sbx$/,
                  message: "ABHA Id should end with @sbx",
                },
              })}
            />
            <button
              className={`!absolute p-1 text-white right-1 top-[3px] rounded ${
                showOtpInput ? "bg-[#006666]" : "bg-[#9A9D9D]"
              }`}
              onClick={handleSubmit2(async () => {
                console.log("jlkjlkj;lkj");
                const data = {
                  patientSBXId: getValues2("otp"),
                  requesterId: "IN2210000258",
                  requesterType: "HIP",
                };

                // try {
                //   const abortController = new AbortController();
                //   await fetchEventSource(
                //     "http://127.0.0.1:9005/patient/auth/userAuthInit",
                //     {
                //       method: "POST",
                //       headers: {
                //         Authorization: `Bearer ${auth?.accessToken}`,
                //         "Content-Type": "application/json",
                //         Accept: "application/json",
                //         credentials: "include",
                //       },
                //       body: JSON.stringify(data),
                //       onmessage(response) {
                //         console.log("response ", response);
                //         var status = JSON.parse(response.data).statusCodeValue;
                //         if (status >= 400)
                //           toast.error(JSON.parse(response.data).body);
                //         else if (status <= 300) {
                //         }
                //         abortController.abort();
                //       },
                //       onclose(resp) {
                //         console.log(resp);
                //         abortController.abort();
                //       },
                //       onerror(error) {
                //         console.log(error);
                //         abortController.abort();
                //         throw new Error(error);
                //       },
                //       signal: abortController.signal,
                //     }
                //   );
                // } catch (err) {
                //   console.log(err);
                //   // toast.error(err);
                // }
              })}
            >
              Confirm
            </button>
          </div>
          <p className="errorMsg">{errors2.otp?.message}</p>
        </div>
      </div>
    </div>
  );
}

export default AbhaVerify;
