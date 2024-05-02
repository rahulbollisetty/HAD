import React from 'react'
import { useNavigate } from "react-router-dom"

function InvalidToken() {
    const goBack = () => navigate("/login");

    return (
        <section>
            <h1 className='text-center'>Invalid Token</h1>
            <br />
            <p>You do not have access to the requested page.</p>
            <div className="flexGrow">
                <button onClick={goBack}>Go Back</button>
            </div>
        </section>
    )
}

export default InvalidToken