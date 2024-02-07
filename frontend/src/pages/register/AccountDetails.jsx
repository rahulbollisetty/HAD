function AccountDetails() {
  return (
    <div className='bg-white h-screen'>
      <div className='bg-white mx-10x text-white h-screen'>
        <div className='h-4'></div>
        <div className='mx-40 m-10 border  border-black rounded-md'>
          <div className='my-5 h-20 bg-[#98a2a3] flex items-center justify-center text-3xl font-semibold text-white py-5'>
            Welcome to MediSync
          </div>

          <div className='h-40 flex items-center justify-center'>
            <div>
              <div className='h-full flex items-center justify-between'>
                <div className='w-24 h-1 bg-[#02685A] my-2 rounded-l-lg'></div>
                <div className='w-20 h-20 bg-[#02685A] mx-1.5 my-1.5 rounded-full'></div>
                <div className='w-36 h-1 bg-[#02685A] my-2'></div>
                <div className='w-23 h-23 bg-[#5AAC74] rounded-full'>
                  <div className='w-20 h-20 bg-[#02685A] mx-1.5 my-1.5 rounded-full'></div>
                </div>
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

          <div className='flex px-32 bg-white mt-10'>
            <p className='font-semibold py-1.5 text-[#5F5F5F] text-lg'>
              Hello Doc, It is important to fill all the details to use the
              platform !
            </p>
          </div>

          <div className='my-12 bg-white px-32 text-black'>
            <div className='flex mb-3'>
              <div className='flex-1'>
                <p className='text-sm font-semibold text-[#787887]'>Name*</p>
                <input className='mt-3 rounded-md w-full' type='text' />
              </div>
              <div className='flex-1 mx-10'>
                <p className='text-sm font-semibold text-[#787887]'>
                  Mobile Number*
                </p>
                <input className='mt-3 rounded-md w-full' type='text' />
              </div>
              <div className='flex-1'>
                <p className='text-sm font-semibold text-[#787887]'>
                  Email Address*
                </p>
                <input className='mt-3 rounded-md w-full' type='text' />
              </div>
            </div>

            <div className='flex'>
              <div className='flex-1'>
                <p className='text-sm mt-10 font-semibold text-[#787887]'>
                  Registration Details*
                </p>
                <input className='rounded-md my-3 w-full' type='text' />
              </div>
              <div className='flex-1 mx-10'></div>
              <div className='flex-1'></div>
            </div>

            <div className='mt-10 w-2/3 flex'>
              <div className='flex-1'>
                <p className='text-sm mr-auto mb-3 font-semibold text-[#787887]'>
                  Gender*
                </p>
                <div className='flex items-center justify-between'>
                  <div className='flex-grow'>
                    <input
                      className=''
                      type='radio'
                      id='Male'
                      value='Male'
                      name='gender'
                    />
                    <label
                      className='text-sm font-semibold text-[#787887] ml-2'
                      htmlFor='Male'
                    >
                      Male
                    </label>
                  </div>
                  <div className='flex-grow'>
                    <input
                      className=''
                      type='radio'
                      id='Female'
                      value='Female'
                      name='gender'
                    />
                    <label
                      className='text-sm font-semibold text-[#787887] ml-2'
                      htmlFor='Female'
                    >
                      Female
                    </label>
                  </div>
                  <div className='flex-grow'>
                    <input
                      className=''
                      type='radio'
                      id='Others'
                      value='Others'
                      name='gender'
                    />
                    <label
                      className='text-sm font-semibold text-[#787887] ml-2'
                      htmlFor='Others'
                    >
                      Others
                    </label>
                  </div>
                </div>
              </div>
              <div className='flex-1'>
                <label
                  className='text-sm font-semibold block text-[#787887]'
                  htmlFor='birthday'
                >
                  Date Of Birth(MM/DD/YYYY)*
                </label>
                <input
                  type='date'
                  id='birthday'
                  name='birthday'
                  className='mt-2 rounded-md'
                />
              </div>
            </div>

            <hr className='w-full h-0.5 bg-[#DDDDDD] mt-10' />

            <div className='flex mt-3 mb-3'>
              <div className='flex-1'>
                <p className='text-sm font-semibold text-[#787887]'>
                  Address Line*
                </p>
                <input className='mt-3 rounded-md w-full' type='text' />
              </div>
              <div className='flex-1 mx-10'>
                <p className='text-sm font-semibold text-[#787887]'>Town*</p>
                <input className='mt-3 rounded-md w-full' type='text' />
              </div>
              <div className='flex-1'>
                <p className='text-sm font-semibold text-[#787887]'>Pincode*</p>
                <input className='mt-3 rounded-md w-full' type='number' />
              </div>
            </div>

            <div className='flex'>
              <div className='flex-1'>
                <p className='text-sm font-semibold text-[#787887] mb-2'>
                  State*
                </p>
                <input
                  className='w-full rounded-md'
                  type='text'
                  name=''
                  id=''
                />
              </div>
              <div className='flex-1 mx-10'></div>
              <div className='flex-1'></div>
            </div>

            <div className='w-full h-0.5 bg-[#DDDDDD] mt-10 my-2'></div>

            <div className='flex mt-3 mb-3'>
              <div className='flex-1'>
                <p className='text-sm font-semibold text-[#787887]'>
                  Set Username*
                </p>
                <input className='mt-3 rounded-md w-full' type='text' />
              </div>
              <div className='flex-1 mx-10'>
                <p className='text-sm font-semibold text-[#787887]'>
                  Set Password*
                </p>
                <input className='mt-3 rounded-md w-full' type='password' />
              </div>
            </div>
          </div>

          <div className='w-full h-0.5 bg-[#DDDDDD] mt-20 my-2 '></div>
          <div className='pl-80 my-5 pr-32'>
            <button className='bg-[#FFA000] block font-bold w-56 h-12 text-xl rounded-md ml-auto'>
              Next
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AccountDetails;
