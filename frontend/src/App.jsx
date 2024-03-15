import Login from "./pages/Login/Login";
import Register from "./pages/register/Register";
import PracticeDetails from "./pages/register/PracticeDetails";
import { PatientScreen } from "./pages/PatientScreen/PatientScreen";
import LinkHPR from "./pages/register/LinkHPR";
import AccountDetails from "./pages/register/AccountDetails";
import { Router, Route, Routes } from "react-router-dom";
import Layout from "./pages/Layout";
import Unauthorized from "./pages/exceptions/Unauthorized";
import RequireAuth from "./routes/RequireAuth";
import PersistLogin from "./PersistLogin";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        {/* public routes */}
        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />
        {/* <Route path="linkpage" element={<LinkPage />} /> */}
        <Route path="unauthorized" element={<Unauthorized />} />

            <Route path="/" element={<PatientScreen />} />
        {/* we want to protect these routes */}
        <Route element={<PersistLogin/>}>
        <Route element={<RequireAuth allowedRoles={["HEAD_DOCTOR"]} />}>
          </Route>

          {/* <Route element={<RequireAuth allowedRoles={[ROLES.Editor]} />}>
            <Route path="editor" element={<Editor />} />
          </Route> */}


          {/* <Route element={<RequireAuth allowedRoles={[ROLES.Admin]} />}>
            <Route path="admin" element={<Admin />} />
          </Route> */}

          {/* <Route element={<RequireAuth allowedRoles={[ROLES.Editor, ROLES.Admin]} />}>
            <Route path="lounge" element={<Lounge />} />
          </Route> */}

        </Route>

        {/* catch all */}
        {/* <Route path="*" element={<Missing />} /> */}
      </Route>
    </Routes>
  );
}

export default App;
