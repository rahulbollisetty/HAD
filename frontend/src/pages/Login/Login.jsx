const Login = () => {
  return (
    <div className='bg-[#02685A]  w-fill h-screen flex items-center justify-center'>
      <div className='bg-white w-4/5 h-[70%] rounded-[10px]'>
        <div className='bg-black w-[150px] h-[150px] rounded-[50%] ml-[50px] mt-[40px] text-white flex items-center justify-center text-3xl'>
          Logo
        </div>
        <div className='bg-black w-full h-[493px] flex'>
          <div className='bg-white pl-[25%] w-[60%] flex-col'>
            <p className='text-3xl pb-[20px]'>Welcome!</p>
            <p className='text-[#7F8C8D] pb-[10px]'>Username</p>
            <input
              type='text'
              className='w-[100%] mr-[10px] rounded-[5px] mb-[20px]'
            />
            <p className='text-[#7F8C8D] pb-[10px]'>Password</p>
            <input
              type='password'
              className='w-[100%] mr-[10px] rounded-[5px] mb-[20px]'
            />
            <a href='' className='text-[#308EDC]'>
              Forget Password?
            </a>
            <button className='mt-[20px] bg-[#02685A] border-0 rounded-[5px] w-[100%] text-white p-[10px]'>
              LOGIN
            </button>
          </div>
          <div className='bg-white w-[40%]'>
            <img src='./bg.png' className='h-full' />
          </div>
        </div>
      </div>
    </div>
  );
};
export default Login;
