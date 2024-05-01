import { useState, useEffect } from "react";
import AddRecords from "./AddRecords";
import ConsentTable from "./ConsentTable";
import PastRecords from "./PastRecords";
import PropTypes from "prop-types";
import * as React from "react";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { toast } from "react-toastify";
import { jwtDecode } from "jwt-decode";
import useAuth from "../../../hooks/useAuth";

function CustomTabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && <Box sx={{ p: 0 }}>{children}</Box>}
    </div>
  );
}

CustomTabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
};

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}
export default function DoctorTab(id) {
  const [role, setRole] = useState();
  const { auth } = useAuth();

  const decoded = auth?.accessToken ? jwtDecode(auth.accessToken) : undefined;
  useEffect(() => {
    if (id.appointmentId && id.appointmentStatus) {
      setActiveTab(2);
      setappointmentId(id.appointmentId);
      setappointmentStatus(id.appointmentStatus === "Completed");
    }
    setRole(decoded?.role);
  }, []);
  const [activeTab, setActiveTab] = useState(0);
  const [appointmentId, setappointmentId] = useState("");
  const [appointmentStatus, setappointmentStatus] = useState(false);
  const handleDataFromPastRecords = (data, status) => {
    // console.log(data, status);
    setActiveTab(2);
    setappointmentId(data);
    setappointmentStatus(status);
  };
  const handleDataFromAddRecords = (data) => {
    // console.log(data);
    setActiveTab(0);
    toast.info(data);
  };

  const handleChange = (event, newValue) => {
    setActiveTab(newValue);
  };

  return (
    <Box className="w-full mx-2">
      <Box>
        {role === "HEAD_DOCTOR" ||
          (role === "DOCTOR" && (
            <>
              <Tabs
                value={activeTab}
                onChange={handleChange}
                aria-label="basic tabs example"
                variant="fullWidth"
                className="w-full text-sm justify-center flex p-1 items-center border border-[#7B7878] rounded-lg
                bg-white cursor-pointer"
                TabIndicatorProps={{
                  sx: {
                    backgroundColor: "#006666",
                    height: "100%",
                    zIndex: 0,
                    borderRadius: "8px",
                  },
                }}
                sx={{
                  "& button": { borderRadius: 2, zIndex: 20 },
                  "& button.Mui-selected": {
                    color: "white",
                    opacity: 100,
                  },
                }}
              >
                <Tab
                  label="Past Records"
                  sx={{
                    opacity: 100,
                    color: "black",
                    textTransform: "none",
                    fontSize: "1.15rem",
                  }}
                  {...a11yProps(0)}
                />
                <Tab
                  label="Import Records"
                  sx={{
                    opacity: 100,
                    color: "black",
                    textTransform: "none",
                    fontSize: "1.15rem",
                  }}
                  {...a11yProps(1)}
                />
                <Tab
                  label="Add Records"
                  sx={{
                    opacity: 100,
                    color: "black",
                    textTransform: "none",
                    fontSize: "1.15rem",
                  }}
                  {...a11yProps(2)}
                />
              </Tabs>
            </>
          ))}
      </Box>
      {role === "HEAD_DOCTOR" ||
        (role === "DOCTOR" && (
          <>
            <CustomTabPanel value={activeTab} index={0}>
              <PastRecords
                sendDataToParent={handleDataFromPastRecords}
                patientId={id}
              />
            </CustomTabPanel>
            <CustomTabPanel value={activeTab} index={1}>
              <ConsentTable patientId={id} />
            </CustomTabPanel>
            <CustomTabPanel value={activeTab} index={2}>
              <AddRecords
                sendDataToParent={handleDataFromAddRecords}
                status={appointmentStatus}
                appointment_id={appointmentId}
                patientId={id}
              />
            </CustomTabPanel>
          </>
        ))}

      {role === "STAFF" && (
        <CustomTabPanel value={activeTab} index={0}>
          <PastRecords
            sendDataToParent={handleDataFromPastRecords}
            patientId={id}
          />
        </CustomTabPanel>
      )}
    </Box>
  );
}
