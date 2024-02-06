import Profile from "./Profile";
import Sidebar from "../../Sidebar";

const ConsentForm = () =>{
    return(
            <div className='bg-white grow m-3'>
                <Profile/>
                <div className='w-full h-full'>
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
                        <p className='font-semibold text-2xl ml-4 mt-4 mb-4 text-[#444444]'>Consent Request Form</p>
                        <div className="h-[1px] bg-[#827F7F82]"></div>
                        
                        <div className='flex mt-6 ml-6 mr-6 justify-center'>
                            <div className='h-10 text-lg w-full flex items-center p-4 bg-white'>
                                ABHA ID
                            </div>
                            <div className='h-10 text-lg w-full flex items-center ml-8 p-4 bg-white'>
                                Verify OTP
                            </div>
                        </div>
                        <div className='flex ml-6 mr-6 mb-6 justify-center'>
                            <div className='h-10 text-sm w-1/2 flex items-center  p-4 bg-white rounded'>
                                <div className='h-10 text-sm w-2/3 flex items-center border bg-white rounded'>
                                    
                                </div>
                                <div className='h-10 text-lg w-1/3 flex justify-center items-center border text-white bg-[#B4B4BE] rounded'>
                                    Verify
                                </div>
                            </div>
                            <div className='h-10 text-sm w-1/2 flex items-center  ml-4 p-4 bg-white rounded-lg'>
                                <div className='h-10 text-sm w-full flex items-center border mr-2 p-2 bg-white rounded-lg'>
                                
                                </div>
                                -
                                <div className='h-10 text-sm w-full flex items-center border ml-2 mr-2 p-4 bg-white rounded-lg'>
                                    
                                </div>-
                                <div className='h-10 text-sm w-full flex items-center border ml-2 mr-2 p-4 bg-white rounded-lg'>
                                
                                </div>-
                                <div className='h-10 text-sm w-full flex items-center border ml-2 mr-2 p-4 bg-white rounded-lg'>
                                    
                                </div>
                            </div>
                            
                        </div>

                        {/* Row - 2 */}
                        
                        <div className='flex mt-6 ml-6 mr-6 justify-center'>
                            <div className='h-10 text-lg w-1/3 flex items-center p-4 bg-white'>
                                Purpose of Request
                            </div>
                            <div className='h-10 text-lg w-1/3 flex items-center p-4 bg-white'>
                                Requested by
                            </div>
                            <div className='h-10 text-sm w-1/3 flex items-center p-4 bg-white'>
                            </div>
                        </div>
                        <div className='flex ml-6 mr-6 mb-6 justify-center'>
                            <div className='h-10 text-sm w-full flex items-center border p-4 ml-4 mr-7 bg-white rounded'>
                                
                            </div>
                            <div className='h-10 text-sm w-full flex items-center border p-4 ml-4 mr-7 bg-white rounded'>
                                
                            </div>
                            <div className='h-10 text-sm w-full flex items-center p-4 ml-4 mr-7 bg-white'>
                            </div>
                        </div>

                        {/* Row - 3 */}
                        
                        <div className='flex mt-6 ml-6 mr-6 justify-center'>
                            <div className='h-10 text-lg w-full flex items-center p-4 bg-white'>
                                Heatlh info from
                            </div>
                            <div className='h-10 text-lg w-full flex items-center p-4 bg-white'>
                                Heatlh info to
                            </div>
                            <div className='h-10 text-lg w-full flex items-center p-4 bg-white'>
                                Heatlh info type
                            </div>
                        </div>
                        <div className='flex ml-6 mr-6 mb-6 justify-center'>
                            <div className='h-10 text-sm w-full flex items-center border p-4 ml-4 mr-7 bg-white rounded'>
                                
                            </div>
                            <div className='h-10 text-sm w-full flex items-center border p-4 ml-4 mr-7 bg-white rounded'>
                                
                            </div>
                            <div className='h-10 text-sm w-full flex items-center border p-4 ml-4 mr-7 bg-white rounded'>
                                
                            </div>
                        </div>

                        {/* Row - 4 */}
                        
                        <div className='flex mt-6 ml-6 mr-6 justify-center'>
                            <div className='h-10 text-lg w-full flex items-center p-4 bg-white'>
                                Consent Expiry
                            </div>
                            <div className='h-10 text-lg w-full flex items-center p-4 bg-white'>
                                Time
                            </div>
                            <div className='h-10 text-lg w-full flex items-center p-4 bg-white'>
                                
                            </div>
                        </div>
                        <div className='flex ml-6 mr-6 mb-6 justify-center'>
                            <div className='h-10 text-sm w-full flex items-center border p-4 ml-4 mr-7 bg-white rounded'>
                                
                            </div>
                            <div className='h-10 text-sm w-full flex items-center border p-4 ml-4 mr-7 bg-white rounded'>
                                
                            </div>
                            <div className='h-10 text-sm w-full flex items-center p-4 ml-4 mr-7 bg-white'>
                                
                            </div>
                        </div>
                        <div className="h-[1px] bg-[#827F7F82]"></div>
        
                        <div className='flex mt-6 ml-6 mr-6 justify-center'>
                        <div className='h-10 text-lg w-1/3 flex items-center p-4 bg-white'>
                                
                            </div>
                            <div className='h-10 text-lg w-1/3 flex items-center p-4 bg-white'>
                                
                            </div>
                            <div className='h-10 text-lg w-1/3 flex items-center mb-6 p-4 bg-white'>
                                <div className='h-10 text-lg w-1/3 justify-center flex items-center p-4 bg-white border border-[#FFA000] rounded'>
                                    Cancel
                                </div>
                                <div className='h-10 text-lg w-2/3 justify-center flex items-center ml-8 p-4 bg-[#FFA000] rounded'>
                                    Request Consent
                                </div>
                            </div>
                            
                        </div>
                    </div>
                </div>
            </div>    
    );
};

export default ConsentForm;