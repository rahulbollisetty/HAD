function LinkHPR() {
  return (
    <div className="bg-white h-screen">
        <div className="bg-white  mx-10 my-4 text-white h-screen mb-12 ">
            <div className="h-4"></div>
            <div className="mx-40 border  border-black rounded-md">
                <div className="my-5 h-20 bg-[#98a2a3] flex items-center justify-center text-3xl font-semibold text-white py-5">
                  Welcome to MediSync
                </div>

                <div className="h-40 px-32 flex items-center">
                    <div className="w-80 p-4"></div>
                    <div className="bg-white">
                        <div className="h-full p-4 items-center flex flex-grow">
                            
                            <div className="w-24 h-1 bg-[#02685A] my-2 rounded-lg ml-32"></div> 
                            <div className="w-23 h-23 bg-[#5AAC74] rounded-full">
                                <div className="w-20 h-20 bg-[#02685A] mx-1.5 my-1.5 rounded-full"></div>
                            </div>
                            <div className="w-36 h-1 bg-[#DDDDDD] my-2 "></div> 
                            <div className="w-20 h-20 bg-[#DDDDDD] rounded-full"></div>
                            <div className="w-36 h-1 bg-[#DDDDDD] my-2 "></div> 
                            <div className="w-20 h-20 bg-[#DDDDDD] rounded-full"></div>
                            <div className="w-24 h-1 bg-[#DDDDDD] my-2 rounded-lg mr-32"></div> 
                        </div>
                        <div className="flex flex-grow">
                            <p className="ml-60 px-2 text-[#02685A] font-semibold text-xl">Link HPR</p>
                            <p className="ml-24 text-[#7F8C8D] font-semibold text-xl">Account Details</p>
                            <p className="ml-16 px-4 text-[#7F8C8D] font-semibold text-xl">Practice Deatils</p>
                        </div>
                    </div>
                    <div className="w-80 p-4"></div>
                </div>


                <div className="flex px-32 bg-white mt-10">
                    <p className="font-semibold py-1.5 text-[#5F5F5F] text-lg">Create your HPR ID by Clicking on the link:</p> 
                    <button className="mx-36 bg-[#308EDC] h-10 rounded-md font-semibold text-lg w-56">LINK</button>
                </div>
                
                
                <div className="my-12 bg-white px-32 text-black">
                    <p className="text-sm font-semibold text-[#787887]">Enter your Healthcare Professional ID/Username *</p>
                    <div className="flex my-3">
                        <input className="flex-1 h-10 rounded-l-md cursor-pointer" type="text" />
                        <p className="flex-2.84 bg-slate-200 rounded-r-md border border-[#787887] font-semibold py-1 text-lg px-20">@hpr.abdm</p>
                    </div>
                </div>
                
                <div className="my-12 bg-white px-32 text-black">
                    <p className="text-sm font-semibold text-[#787887]">Password</p>
                    <input className = "w-full h-10 my-3 rounded-md" type="password" />
                </div>
                <div className="px-32">
                    <button className="bg-[#02685A] text-md font-semibold h-12 w-56 rounded-md">Get Account Details</button>
                </div>
                <div className="w-full h-0.5 bg-[#DDDDDD] mt-20 my-2 "></div> 
                <div className="pl-80 my-5 pr-32">
                    <button className="bg-[#FFA000] block font-bold w-56 h-12 text-xl rounded-md ml-auto">Next</button>
                </div>
            </div>
        </div>

    </div>
  )
}

export default LinkHPR;