import {
  Button,
  Dialog,
  DialogHeader,
  DialogBody,
  DialogFooter,
} from "@material-tailwind/react";
import { useState } from "react";

const StaffDetail = () => {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(!open);
  return (
    <div>
      <button
        onClick={handleOpen}
        className="inline-flex justify-center items-center gap-[10px] rounded-lg
                                          border border-[#787887] bg-[#F5FEF2] text-[20px] text-[#02685A] font-semibold p-2.5"
      >
        {/* <MdAdd className="h-[35px] w-[35px] m-auto" /> */}
        <div className="relative w-fit font-semibold m-auto text-[20px]">
          View
        </div>
      </button>

      <Dialog open={open} onClose={handleOpen} size="xl">
        <DialogHeader>Staff's Details</DialogHeader>
        <div className="h-[1px] bg-[#827F7F82]"></div>
        <DialogBody>
          <div>
            <div className="w-full">
              <div className="mt-5 flex">
                <div className=" w-32 h-32 m-3 mt-0 rounded-full ">
                  <img
                    className="rounded-full mt-0"
                    src="https://wallpapers.com/images/hd/handsome-giga-chad-hmsvijj0ji4dhedr.jpg"
                    alt="profile photo"
                  />
                </div>
                <div className="w-full">
                  <div className="mt-4 ml-8">
                    <p className="text-xl font-semibold text-[#444444]">
                      B. Rahul
                    </p>
                  </div>
                  <div className="flex mt-2 ml-8 text-l">
                    <div className="flex-1">
                      <span className="font-semibold flex mr-20 text-[#7B7878]">
                        Registration Number*
                        <p className="ml-6 text-black font-medium">123456789</p>
                      </span>
                    </div>
                    <div className="flex-1">
                      <span className="font-semibold flex ml-auto mr-20 text-[#7B7878]">
                        Mobile Number*
                        <p className="ml-6 text-black font-medium">123456789</p>
                      </span>
                    </div>
                    <div className="flex-1">
                      <span className="font-semibold flex ml-auto mr-20 text-[#7B7878]">
                        Email Address
                        <p className="ml-6 text-black font-medium">
                          rahulb01@gmail.com
                        </p>
                      </span>
                    </div>
                  </div>
                  <div className="flex mt-2 ml-8 text-l">
                    <div className="flex-1">
                      <span className="font-semibold flex mr-20 text-[#7B7878]">
                        Date Of Birth(DD/MM/YY)
                        <p className="ml-6 text-black font-medium">06/09/69</p>
                      </span>
                    </div>
                    <div className="flex-1">
                      <span className="font-semibold flex ml-auto mr-20 text-[#7B7878]">
                        Gender
                        <p className="ml-6 text-black font-medium">Male</p>
                      </span>
                    </div>
                    <div className="flex-1">
                      <span className="font-semibold flex ml-auto mr-20 text-[#7B7878]">
                        Role
                        <p className="ml-6 text-black font-medium">
                          Lab Attendant
                        </p>
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
            <div className="grid grid-cols-3 place-items-center gap-3  text-[#7B7878] font-medium font-semibold text-l  p-5">
              <div className="flex flex-col  item-center">
                <p className=" font-semibold ">Address Line*</p>
                <p className="text-black text-center font-medium">Lmao</p>
              </div>
              <div className="flex flex-col item-center">
                <p className=" font-semibold ">Town/City</p>
                <p className="text-black text-center font-medium">Andaman</p>
              </div>
              <div className="flex flex-col item-center">
                <p className=" font-semibold ">Pincde</p>
                <p className="text-black text-center font-medium">472000</p>
              </div>
              <div className="flex flex-col col-span-1 item-center px-0">
                <p className=" pb-2 font-semibold">State</p>
                <p className="text-black text-center font-medium">CG</p>
              </div>
            </div>
          </div>
          <hr className="h-[3px] bg-[#7B7878] mx-2 mt-6 opacity-50	" />
        </DialogBody>
        <DialogFooter>
          <Button variant="filled" className="bg-[#FFA000]">
            <span>Delete Staff</span>
          </Button>
          <Button
            variant="text"
            color="red"
            onClick={handleOpen}
            className="mr-1"
          >
            <span>Cancel</span>
          </Button>
        </DialogFooter>
      </Dialog>
    </div>
  );
};
export default StaffDetail;
