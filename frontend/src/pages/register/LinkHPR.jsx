import { useState } from "react";
import axios from "../../api/axios";
function LinkHPR() {
  const [hprId, setHprId] = useState("");
  const [password, setPassword] = useState("");
  const [doctorDetails, setDoctorDetails] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const RequestBody = {
      hprId: hprId,
      password: password,
    };
    RequestBody.hprId += "@hpr.abdm";
    try {
      const response = await fetch(
        "http://127.0.0.1:9008/hpr/getdoctordetails",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(RequestBody),
        }
      );

      if (!response.ok) {
        throw new Error("Failed to fetch doctor details");
      }
      const data = await response.json();
      setDoctorDetails(data);
      await setErrorMessage("Doctor Details fetched!");
    } catch (error) {
      await setErrorMessage("Error getting Doctor Details!");
    }
  };
  console.log(doctorDetails);

  return (
    <nav>
      <div className='bg-white min-h-screen overflow-auto'>
        <div className='bg-white mx-10 text-white min-h-screen'>
          <div className='h-4'></div>
          <div className='mx-10 border border-black rounded-md'>
            <div className='my-5 h-20 bg-[#98a2a3] flex items-center justify-center text-3xl font-semibold text-white py-5'>
              Welcome to MediSync
            </div>

            <div className='h-40 flex items-center justify-center'>
              <div>
                <div className='h-full flex items-center justify-between'>
                  <div className='w-24 h-1 bg-[#02685A] my-2 rounded-l-lg'></div>
                  <div className='w-23 h-23 bg-[#5AAC74] rounded-full'>
                    <div className='w-20 h-20 bg-[#02685A] mx-1.5 my-1.5 rounded-full'></div>
                  </div>
                  <div className='w-36 h-1 bg-[#DDDDDD] my-2'></div>
                  <div className='w-20 h-20 bg-[#DDDDDD] rounded-full'></div>
                  <div className='w-36 h-1 bg-[#DDDDDD] my-2'></div>
                  <div className='w-20 h-20 bg-[#DDDDDD] rounded-full'></div>
                  <div className='w-24 h-1 bg-[#DDDDDD] my-2 rounded-r-lg'></div>
                </div>

                <div className='flex mt-2'>
                  <p className='ml-24 text-[#02685A] font-semibold text-xl'>
                    Link HPR
                  </p>
                  <p className='ml-32 text-[#7F8C8D] font-semibold text-xl'>
                    Account Details
                  </p>
                  <p className='ml-20 text-[#7F8C8D] font-semibold text-xl'>
                    Practice Details
                  </p>
                </div>
              </div>
            </div>

            <div className='flex px-10 bg-white mt-10'>
              <p className='font-semibold py-1.5 text-[#5F5F5F] text-lg'>
                Create your HPR ID by Clicking on the link:
              </p>
              <button className='mx-8 bg-[#308EDC] h-10 rounded-md font-semibold text-lg w-56'>
                <a
                  href='https://hpridsbx.abdm.gov.in/register'
                  target='_blank'
                  rel='noopener noreferrer'
                >
                  LINK
                </a>
              </button>
            </div>
            <form onSubmit={handleSubmit}>
              <div className='my-12 px-10 text-black'>
                <p className='text-sm font-semibold text-[#787887]'>
                  Enter your Healthcare Professional ID/Username *
                </p>
                <div className='flex w-full'>
                  <label className='flex-1  cursor-pointer'>
                    <input
                      className='w-full h-10 rounded-l-md'
                      type='text'
                      value={hprId}
                      onChange={(e) => setHprId(e.target.value)}
                    />
                  </label>
                  <p className='flex-2.84 bg-slate-200 rounded-r-md border border-[#787887] font-semibold py-1 text-lg px-20'>
                    @hpr.abdm
                  </p>
                </div>
              </div>
              <div className='my-12 bg-white px-10 text-black'>
                <p className='text-sm font-semibold text-[#787887]'>Password</p>
                <label>
                  <input
                    className='w-full h-10 my-3 rounded-md'
                    type='password'
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                </label>
              </div>
              <div className='px-10'>
                <button
                  className='bg-[#02685A] text-md font-semibold h-12 w-56 rounded-md'
                  type='submit'
                >
                  Get Account Details
                </button>
              </div>
            </form>
            <div className='w-full h-0.5 bg-[#DDDDDD] mt-20 my-2'></div>
            <div className='pl-80 my-5 pr-32'>
              <button className='bg-[#FFA000] block font-bold w-56 h-12 text-xl rounded-md ml-auto'>
                Next
              </button>
            </div>
          </div>
        </div>
        {/* {errorMessage && (
        <div
          className={
            errorMessage === "Error getting doctor Information"
              ? "bg-red-200"
              : "bg-green-300"
          }
          role='alert'
        >
          {errorMessage}
        </div>
      )} */}
      </div>
    </nav>
  );
}
export default LinkHPR;
