import { MdOutlineCalendarToday,MdOutlineSettings,MdPeopleOutline, MdExitToApp } from "react-icons/md";
import {PiStethoscope } from "react-icons/pi";

const Sidebar = () =>{
    return (
        <>
        <div className="h-[100vh] top-0 bg-white w-[250px] left-0">
            <div className='h-[64px] bg-black'>
            <div className="h-full [font-family:'Inter',Helvetica] font-bold text-white text-[34px] flex items-center justify-center">
                MediSync
          </div>
            </div>
            <div className='h-4/5'>
                <div class="flex flex-col">
                    <div className='h-[80px] inactive align-center'>
                        <div className='inline-flex gap-[15px]  w-fit relative left-7 top-1/3'>
                            <MdOutlineCalendarToday className="h-[25px] w-[25px] m-auto"/>
                            <div className="relative w-fit font-semibold text-[20px]">
                                Schedule
                            </div>
                        </div>
                    </div>
                    <div className='h-[80px] active align-center'>
                        <div className='inline-flex gap-[15px] w-fit relative left-[30px] top-1/3'>
                            <MdPeopleOutline className="h-[25px] w-[25px] m-auto"/>
                            <div className="relative w-fit font-semibold text-[20px]">
                                Patients
                            </div>
                        </div>
                    </div>
                    <div className='h-[80px] inactive align-center'>
                        <div className='inline-flex gap-[15px]  w-fit relative left-[30px] top-1/3'>
                            <PiStethoscope className="h-[25px] w-[25px] font-semibold m-auto"/>
                            <div className="relative w-fit font-semibold text-[20px]">
                                Doctors
                            </div>
                        </div>
                    </div>

                    <div className='h-[80px] inactive align-center'>
                        <div className='inline-flex gap-[15px] w-fit relative left-[30px] top-1/3'>
                        <MdOutlineSettings className="h-[25px] w-[25px] m-auto"/>
                            <div className="relative w-fit font-semibold text-[20px]">
                                Settings
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>
            <div className="h-1/12 m-[10px]">
            <div className='inline-flex gap-[15px] px-[25px] py-[10px] text-white w-fit relative left-[15px] top-1/3 bg-[#006666] rounded-[10px] overflow-hidden shadow-[-2px_-1px_3px_#00000040]'>
                            <MdExitToApp className="h-[25px] w-[25px] m-auto"/>
                            <div className="relative w-fit font-semibold text-[20px]">
                                Logout
                            </div>
                        </div>
            </div>

          </div>
        </>
    );
}

export default Sidebar;

