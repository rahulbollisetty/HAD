function PracticeDetails() {
  return (
    <div className='bg-white h-screen'>
      <div className='bg-white  mx-10 my-4 text-white h-screen mb-12 '>
        <div className='h-4'></div>
        <div className='mx-40 border  border-black rounded-md'>
          <div className='my-5 h-20 bg-[#98a2a3] flex items-center justify-center text-3xl font-semibold text-white py-5'>
            Welcome to MediSync
          </div>

          <div className='h-40 flex items-center justify-center'>
            <div>
              <div className='h-full flex items-center justify-between'>
                <div className='w-24 h-1 bg-[#02685A] my-2 rounded-l-lg'></div>
                <div className='w-20 h-20 bg-[#02685A]  my-1.5 rounded-full'></div>
                <div className='w-36 h-1 bg-[#02685A] my-2'></div>
                <div className='w-20 h-20 bg-[#02685A]  my-1.5 rounded-full'></div>
                <div className='w-36 h-1 bg-[#02685A] my-2'></div>
                <div className='w-23 h-23 bg-[#5AAC74] rounded-full'>
                  <div className='w-20 h-20 bg-[#02685A] mx-1.5 my-1.5 rounded-full'></div>
                </div>
                <div className='w-24 h-1 bg-[#DDDDDD] my-2 rounded-r-lg'></div>
              </div>

              <div className='flex mt-2'>
                <p className='ml-24 text-[#7F8C8D] font-semibold text-xl'>
                  Link HPR
                </p>
                <p className='ml-32 text-[#7F8C8D] font-semibold text-xl'>
                  Account Details
                </p>
                <p className='ml-20 text-[#02685A] font-semibold text-xl'>
                  Practice Details
                </p>
              </div>
            </div>
          </div>

          <div className='flex mx-32 bg-white mt-10'>
            <p className='font-semibold py-1.5 text-[#5F5F5F] text-lg'>
              Hello Doc, It is important to fill all the details to use the
              platform !
            </p>
          </div>

          <div className='flex my-3 mx-32'>
            <div className='flex-1'>
              <p className='text-sm font-semibold text-[#787887]'>CLinic ID*</p>
              <input className='mt-3 rounded-md w-full' type='text' />
            </div>
            <div className='flex-1 mx-10'>
              <p className='text-sm font-semibold text-[#787887]'>
                Clinic Name*
              </p>
              <input className='mt-3 rounded-md w-full' type='text' />
            </div>
            <div className='flex-1'>
              <p className='text-sm font-semibold text-[#787887]'>
                Clinic Specializatin*
              </p>
              <input className='mt-3 rounded-md w-full' type='text' />
            </div>
          </div>

          <div className='flex my-6 mx-32'>
            <div className='flex-1 mr-9'>
              <p className='text-sm font-semibold text-[#787887]'>Clinic Address*</p>
              <input className='mt-3 rounded-md w-full' type='text' />
            </div>
            <div className='flex-2'>
              <p className='text-sm font-semibold text-[#787887]'>
                Town / City*
              </p>
              <input className='mt-3 rounded-md pl-[199px]  w-full' type='number' />
            </div>
          </div>

          <div className='flex my-3 mx-32'>
            <div className='flex-1'>
              <p className='text-sm font-semibold text-[#787887]'>State*</p>
              <input className='mt-3 rounded-md w-full' type='text' />
            </div>
            <div className='flex-1 mx-10'>
              <p className='text-sm font-semibold text-[#787887]'>
                Pincode*
              </p>
              <input className='mt-3 rounded-md w-full' type='text' />
            </div>
            <div className='flex-1'>
              <p className='text-sm font-semiboldtext-[#787887]'>
                GSTIN*
              </p>
              <input className='mt-3 rounded-md w-full' type='text' />
            </div>
          </div>

          <div className='w-full h-0.5 bg-[#DDDDDD] mt-20 my-2 '></div>

          <div className='flex items-center justify-center my-5'>
            <button className='bg-[#FFA000] block font-bold w-56 h-12 text-xl rounded-md mx-auto'>
              SUBMIT
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PracticeDetails;
