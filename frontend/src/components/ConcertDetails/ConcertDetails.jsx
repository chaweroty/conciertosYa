import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams, Link } from "react-router-dom";
import ConcertSeatLayout from '../seat/ConcertSeatLayout';
import { MdOutlineChair } from 'react-icons/md';

const API_URL = "http://localhost:8080/events";

const ConcertDetails = () => {
  const { eventId } = useParams();
  const [eventDetails, setEventDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchEventDetails = async () => {
      try {
        setLoading(true);
        const response = await axios.get(`${API_URL}/get/${eventId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setEventDetails(response.data.ourEvents); // Ajusta según la estructura de tu respuesta
      } catch (err) {
        setError("Error al obtener los detalles del evento.");
        console.error("Error fetching event details:", err);
      } finally {
        setLoading(false);
      }
    };

    if (eventId) {
      fetchEventDetails();
    }
  }, [eventId, token]);

  if (loading) {
    return <p className="text-xl mt-10">Cargando detalles del concierto...</p>;
  }

  if (error) {
    return <p className="text-xl mt-10 text-red-500">{error}</p>;
  }

  if (!eventDetails) {
    return <p className="text-xl mt-10">Evento no encontrado.</p>;
  }

  return (
    <div className="concert-details-container">
      <div className="bg-gray-100">
        <div className="container mx-auto px-4 py-8">
          <div className="flex flex-wrap -mx-4">
            {/* Imagen y descripción */}
            <div className="w-full lg:w-1/3 px-4 mb-8 lg:mb-0">
              <img
                className="w-full rounded-lg shadow-lg"
                src={eventDetails.image}
                alt={eventDetails.name}
              />
              <p className="text-lg mt-6">{eventDetails.description}</p>
            </div>

            {/* Información principal del evento */}
            <div className="w-full lg:w-2/3 px-4">
              <h1 className="text-4xl font-bold mb-4">{eventDetails.name}</h1>
              <p className="text-lg mb-6">
                Un evento inolvidable con {eventDetails.artist.name}.
              </p>

              {/* Detalles del evento */}
              <div className="mb-6">
                <p className="text-xl font-bold mb-2">Género Musical:</p>
                <p className="text-lg">{eventDetails.musicalGenre}</p>
              </div>
              <div className="mb-6">
                <p className="text-xl font-bold mb-2">Fecha y Hora:</p>
                <p className="text-lg">
                  {eventDetails.date} a las {eventDetails.hour}
                </p>
              </div>
              <div className="mb-6">
                <p className="text-xl font-bold mb-2">Lugar:</p>
                <p className="text-lg">{eventDetails.place.name}</p>
                <p className="text-lg">
                  {eventDetails.place.direction}, {eventDetails.place.city}
                </p>
              </div>
              <div className="mb-6">
                <p className="text-xl font-bold mb-2">Estado del Evento:</p>
                <p className="text-lg">{eventDetails.status}</p>
              </div>

              <div className="mb-6">
                <p className="text-xl font-bold mb-2">Precios:</p>
                <p className="text-lg">
                  General: 25 usd 
                </p>
                <p className="text-lg">
                  VIP: 50 usd
                </p>
                <p className="text-lg">
                  Palco: 100 usd
                </p>
              </div>

              {/* Capacidades */}
              <div className="mb-6">
                <p className="text-xl font-bold mb-2">Capacidades:</p>
                <div className="flex items-center gap-x-2">
                <MdOutlineChair className="text-lg text-neutral-500 -rotate-300" />
                <p className="text-lg">
                  General: {eventDetails.place.capacityGeneral} asientos
                </p>
                </div>
                <div className="flex items-center gap-x-2">
                <MdOutlineChair className="text-lg text-black-500 -rotate-300" />
                <p className="text-lg">
                  VIP: {eventDetails.place.capacityVip} asientos
                </p>
                </div>

                <div className="flex items-center gap-x-2">
                <MdOutlineChair className="text-lg text-orange-500 -rotate-300" />
                <p className="text-lg">
                  Palco: {eventDetails.place.capacityPalco} asientos
                </p>
                </div>
              </div>
              <ConcertSeatLayout
               capacityGeneral={eventDetails.place.capacityGeneral}
               priceGen={eventDetails.place.id.priceGener}
               discountGen ={eventDetails.place.id.discountGen}

               capacityVip={eventDetails.place.capacityVip}
               priceVip={eventDetails.place.id.priceVip}
               discountVip={eventDetails.place.id.discountVip}
         
               capacityPalco={eventDetails.place.capacityPalco}
               pricePalco={eventDetails.place.id.pricePalco}
               discountPalco={eventDetails.place.id.discountPalco}

                />
           
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ConcertDetails;
