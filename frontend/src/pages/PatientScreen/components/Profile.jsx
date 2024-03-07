function Profile() {
  return (
    <div>
      <div className='w-full'>
        <div className='mt-5 flex'>
          <div className='ml-8 w-20 h-20 rounded-full'>
            <img
              className='rounded-full mt-6'
              src='https://images.pexels.com/photos/3394658/pexels-photo-3394658.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'
              alt='profile photo'
            />
          </div>
          <div className='w-full'>
            <div className='mt-4 ml-8'>
              <p className='text-xl font-semibold'>Mukesh Kumar</p>
            </div>
            <div className='flex mt-2 ml-8 text-sm'>
              <div className='flex-1'>
                <span className='font-semibold flex mr-20 text-[#7B7878]'>
                  Gender:
                  <p className='ml-6 text-black'>Male</p>
                </span>
              </div>
              <div className='flex-1'>
                <span className='font-semibold flex ml-auto mr-20 text-[#7B7878]'>
                  Age:
                  <p className='ml-6 text-black'>32</p>
                </span>
              </div>
              <div className='flex-1'>
                <span className='font-semibold flex ml-auto mr-20 text-[#7B7878]'>
                  ABHA Id:
                  <p className='ml-6 text-black'>123456789</p>
                </span>
              </div>
              <div className='flex-1'>
                <span className='font-semibold flex ml-auto mr-20 text-[#7B7878]'>
                  Email id:
                  <p className='ml-6 text-black'>mukesh@gmail.com</p>
                </span>
              </div>
            </div>
            <div className='flex mt-4 ml-8 text-sm'>
              <div className='w-1/4'>
                <span className='font-semibold flex mr-52 text-[#7B7878]'>
                  Blood Group:
                  <p className='ml-6 text-black'>B+</p>
                </span>
              </div>
              <div className='w-1/4'>
                <span className='font-semibold flex mr-20 text-[#7B7878]'>
                  Mobile Number:
                  <p className='ml-6 text-black '>987654321</p>
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    <hr className="bg-[#7B7878] h-0.25 mt-4"/>
    </div>
  );
}

export default Profile;
