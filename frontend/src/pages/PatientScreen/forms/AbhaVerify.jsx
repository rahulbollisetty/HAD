import React from "react";
import { useForm } from "react-hook-form";
import { EventStreamContentType, fetchEventSource } from "@microsoft/fetch-event-source";
import useAuth from "../../../hooks/useAuth";
import toast from "react-hot-toast";

function AbhaVerify() {
  const {
    register: register1,
    handleSubmit: handleSubmit1,
    reset: reset1,
    getValues: getValues1,
    formState: { errors: error1 },
  } = useForm();

  const {
    register: register2,
    handleSubmit: handleSubmit2,
    reset: reset2,
    getValues: getValues2,
    formState: { errors: errors2 },
  } = useForm();

  const { auth } = useAuth();

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
                  await fetchEventSource(
                    "http://127.0.0.1:9005/patient/userAuthInit",
                    {
                      method: "POST",
                      headers: {
                        Authorization: `Bearer ${auth?.accessToken}`,
                        "Content-Type": "application/json",
                        credentials:true
                      },
                      body: JSON.stringify(data),
                      onopen(response) {
                        if (
                          response.ok &&
                          response.headers.get("content-type") ===
                            EventStreamContentType
                        ) {
                          return; // everything's good
                        } else if (
                          response.status >= 400 &&
                          response.status < 500 &&
                          response.status !== 429
                        ) {
                          // client-side errors are usually non-retriable:
                          throw new Error("Client-Error");
                        } else {
                          throw new Error("Server-side-Error");
                        }
                      },
                      onmessage(response) {
                        console.log("response ", response);

                        abortController.abort();
                      },
                      onclose(resp) {
                        console.log("closed");
                      },
                      onerror(error) {
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
  );
}

export default AbhaVerify;
