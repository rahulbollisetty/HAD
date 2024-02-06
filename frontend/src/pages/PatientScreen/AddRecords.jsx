import Sidebar from "../Sidebar";
import Profile from "./components/Profile";
function AddRecords() {
  return (
    <div className='flex flex-row'>
      <div>
        <Sidebar />
      </div>
      <div className='basis-full bg-[#F1F5FC] h-screen'>
        <div className='flex flex-col h-full'>
          <div className='h-[64px] w-full pb-16 bg-white'></div>
          <div className='bg-white grow m-3'>
            <Profile />
            <div className='w-full h-screen'>
              <div className='flex mt-6 justify-center w-full'>
                <div className='h-10 text-sm justify-center w-80 flex items-center border border-[#7B7878] rounded-l-sm bg-white'>
                  Import Records
                </div>
                <div className='h-10 text-sm justify-center w-80 flex items-center text-white bg-[#006666]'>
                  Add Records
                </div>
                <div className='h-10 text-sm justify-center w-80 flex items-center border border-[#7B7878] bg-white rounded-r-sm'>
                  Billing
                </div>
              </div>

              <div className='border mx-8 my-6 border-[#006666] rounded-md border-l-4'>
                <p className='font-semibold ml-4 mt-4'>Vital Signs</p>
                <div className='bg-yellow-100 mt-12'>
                  <div className='flex'>
                    <p className=''>Weight(kg)</p>
                    <input type='number' name='' id='' />
                    <p>Blood Pressue(mm/Hg)</p>
                    <input type='number' name='' id='' />
                  </div>

                  <div className='flex'>
                    <p className=''>Weight(kg)</p>
                    <input type='number' name='' id='' />
                    <p>Blood Pressue(mm/Hg)</p>
                    <input type='number' name='' id='' />
                  </div>
                  
                  <div className='flex'>
                    <p className=''>Weight(kg)</p>
                    <input type='number' name='' id='' />
                    <p>Blood Pressue(mm/Hg)</p>
                    <input type='number' name='' id='' />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
export default AddRecords;
