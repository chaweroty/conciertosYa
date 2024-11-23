// ConcertDetails.jsx
import React from 'react';
import { useParams } from 'react-router-dom';
import ConcertSeatLayout from '../seat/ConcertSeatLayout';
import { Link } from 'react-router-dom';

const ConcertDetails = () => {
  const { artistId } = useParams();

  return (
    <div className="concert-details-container">
      <div className="bg-gray-100">
        <div className="container mx-auto px-4 py-8">
          <div className="flex flex-wrap -mx-4">
            <div className="w-full lg:w-1/3 px-4 mb-8 lg:mb-0">
              <img
                className="w-full rounded-lg shadow-lg"
                src="https://upload.wikimedia.org/wikipedia/commons/d/d8/Linkin_Park_-_From_Zero_Lead_Press_Photo_-_James_Minchin_III.jpg"
                alt="Concert Image"
              />
              <p className="text-lg mb-6">
                Linkin Park es una de las bandas más icónicas y revolucionarias del
                rock alternativo y nu-metal. Formada en 1996 en Agoura Hills,
                California, la banda se destacó por su capacidad de fusionar géneros
                como el rock, hip-hop, y la música electrónica, creando un sonido único
                que marcó una generación. Con Chester Bennington como vocalista
                principal y Mike Shinoda como rapero, vocalista y compositor, la banda
                alcanzó reconocimiento mundial con su álbum debut Hybrid Theory (2000),
                que incluyó éxitos como "In the End", "Crawling", y "One Step Closer".
                Este álbum fue certificado diamante en Estados Unidos y sigue siendo uno
                de los más vendidos del siglo XXI.
              </p>
            </div>
            <div className="w-full lg:w-2/3 px-4">
              <h1 className="text-4xl font-bold mb-4">Linkin Park en vivo en concierto</h1>
              <p className="text-lg mb-6">Join us for an unforgettable evening of music.</p>
              <div className="mb-6">
                <p className="text-xl font-bold mb-2">When:</p>
                <p className="text-lg">Friday, April 15th at 8:00 PM</p>
              </div>
              <div className="mb-6">
                <p className="text-xl font-bold mb-2">Where:</p>
                <p className="text-lg">The Fillmore Auditorium</p>
                <p className="text-lg">1805 Geary Blvd, San Francisco, CA</p>
              </div>
              <div className="mb-6">
                <p className="text-xl font-bold mb-2">Tickets:</p>
                <p className="text-lg">$35 - General </p>
                <p className="text-lg">$75 - VIP</p>
                <p className="text-lg">$150 - Palco</p>
              </div>

              <ConcertSeatLayout />
              <Link to="/Checkout">
                <button
                  className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                  type="button"
                >
                  Buy Tickets
                </button>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ConcertDetails;
