import axios from "axios";
import React, { useEffect, useState } from "react";

const API_URL = "http://localhost:8080/places";

const PlaceTable = () => {
  const [places, setPlaces] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [modalData, setModalData] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [selectedPlace, setSelectedPlace] = useState(null);

  const token = localStorage.getItem("token");

  useEffect(() => {
    fetchPlaces();
  }, []);

  const fetchPlaces = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`${API_URL}/get-all`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setPlaces(response.data.ourPlacesList || []);
    } catch (err) {
      setError(
        "Error al obtener los lugares. Verifica tu conexión o permisos."
      );
      console.error("Error fetching places:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = () => {
    setIsEditing(false);
    setModalData({
      name: "",
      capacityGeneral: "",
      capacityVip: "",
      capacityPalco: "",
      state: "",
      city: "",
      direction: "",
      image: "",
    });
    setShowModal(true);
  };

  const handleEdit = (place) => {
    setIsEditing(true);
    setModalData(place);
    setShowModal(true);
  };

  const handleDelete = async () => {
    try {
      await axios.delete(`${API_URL}/delete/${selectedPlace.id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setPlaces(places.filter((place) => place.id !== selectedPlace.id));
      setIsDeleteModalOpen(false);
    } catch (err) {
      setError("Error al eliminar el lugar.");
    }
  };

  const openDeleteModal = (place) => {
    setSelectedPlace(place);
    setIsDeleteModalOpen(true);
  };

  const validateData = () => {
    const {
      name,
      capacityGeneral,
      capacityVip,
      capacityPalco,
      state,
      city,
      direction,
      image,
    } = modalData;
    if (
      !name ||
      !capacityGeneral ||
      !capacityVip ||
      !capacityPalco ||
      !state ||
      !city ||
      !direction ||
      !image
    ) {
      setError("Todos los campos son obligatorios.");
      return false;
    }
    return true;
  };
  const handleSave = async () => {
    if (!validateData()) {
      return;
    }
    try {
      if (isEditing) {
        await axios.put(`${API_URL}/update/${modalData.id}`, modalData, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
      } else {
        await axios.post(`${API_URL}/add`, modalData, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
      }
      setShowModal(false);
      fetchPlaces();
    } catch (err) {
      setError("Error al guardar el evento.");
      console.error("Error saving event:", err);
    }
  };
  const handleCancel = () => {
    setShowModal(false);
    setModalData(null);
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Lugares</h1>

      {loading && <p>Cargando lugares...</p>}
      {error && <p className="text-red-500">{error}</p>}

      {!loading && !error && (
        <div>
          <button
            onClick={handleCreate}
            className="mb-4 p-2 bg-blue-500 text-white rounded"
          >
            Crear Lugar
          </button>
          <table className="table-auto w-full border-collapse border border-gray-300">
            <thead>
              <tr className="bg-gray-200">
                <th className="border border-gray-300 px-4 py-2">Nombre</th>
                <th className="border border-gray-300 px-4 py-2">Dirección</th>
                <th className="border border-gray-300 px-4 py-2">Ciudad</th>
                <th className="border border-gray-300 px-4 py-2">
                  Capacidad General
                </th>
                <th className="border border-gray-300 px-4 py-2">
                  Capacidad VIP
                </th>
                <th className="border border-gray-300 px-4 py-2">
                  Capacidad Palco
                </th>
                <th className="border border-gray-300 px-4 py-2">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {places.map((place) => (
                <tr key={place.id} className="hover:bg-gray-100">
                  <td className="border border-gray-300 px-4 py-2">
                    {place.name}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {place.address}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {place.city}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {place.capacityGeneral}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {place.capacityVip}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {place.capacityPalco}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    <button
                      onClick={() => handleEdit(place)}
                      className="px-4 py-2 bg-yellow-500 text-white rounded mr-2"
                    >
                      Editar
                    </button>
                    <button
                      onClick={() => openDeleteModal(place)}
                      className="ml-2 px-4 py-2 font-medium text-white bg-red-600 rounded-md hover:bg-red-500 focus:outline-none"
                    >
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {isDeleteModalOpen && selectedPlace && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white rounded-lg shadow p-6 w-1/3 text-center">
            <h2 className="text-lg font-bold mb-4">Confirmar Eliminación</h2>
            <p className="mb-4">
              ¿Estás seguro de que quieres eliminar <b>{selectedPlace.name}</b>?
            </p>
            <div className="flex justify-center">
              <button
                onClick={() => setIsDeleteModalOpen(false)}
                className="px-4 py-2 bg-gray-200 rounded mr-2"
              >
                Cancelar
              </button>
              <button
                onClick={handleDelete}
                className="px-4 py-2 bg-red-600 text-white rounded"
              >
                Eliminar
              </button>
            </div>
          </div>
        </div>
      )}

      {showModal && (
        <div className="fixed inset-0 z-50 flex justify-center items-center bg-black bg-opacity-50">
          <div className="bg-white p-6 rounded-lg shadow-lg w-1/3 max-h-screen overflow-y-auto">
            <h2 className="text-lg font-bold mb-4">
              {isEditing ? "Editar Lugar" : "Crear Lugar"}
            </h2>
            <form>
              <div className="mb-4">
                <label className="block text-sm font-semibold">Nombre</label>
                <input
                  type="text"
                  value={modalData.name}
                  onChange={(e) =>
                    setModalData({ ...modalData, name: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>

              <div className="mb-4">
                <label className="block text-sm font-semibold">
                  Capacida de General
                </label>
                <input
                  type="text"
                  value={modalData.capacityGeneral}
                  onChange={(e) =>
                    setModalData({
                      ...modalData,
                      capacityGeneral: e.target.value,
                    })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>

              <div className="mb-4">
                <label className="block text-sm font-semibold">
                  Capacida de VIP
                </label>
                <input
                  type="text"
                  value={modalData.capacityVip}
                  onChange={(e) =>
                    setModalData({ ...modalData, capacityVip: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>

              <div className="mb-4">
                <label className="block text-sm font-semibold">
                  Capacida de palco
                </label>
                <input
                  type="text"
                  value={modalData.capacityPalco}
                  onChange={(e) =>
                    setModalData({
                      ...modalData,
                      capacityPalco: e.target.value,
                    })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>

              <div className="mb-4">
                <label className="block text-sm font-semibold">Estado</label>
                <input
                  type="text"
                  value={modalData.state}
                  onChange={(e) =>
                    setModalData({ ...modalData, state: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>

              <div className="mb-4">
                <label className="block text-sm font-semibold">ciudad</label>
                <input
                  type="text"
                  value={modalData.city}
                  onChange={(e) =>
                    setModalData({ ...modalData, city: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>

              <div className="mb-4">
                <label className="block text-sm font-semibold">Direccion</label>
                <input
                  type="text"
                  value={modalData.direction}
                  onChange={(e) =>
                    setModalData({ ...modalData, direction: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>

              <div className="mb-4">
                <label className="block text-sm font-semibold">Imagen</label>
                <input
                  type="number"
                  value={modalData.image}
                  onChange={(e) =>
                    setModalData({ ...modalData, image: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="flex justify-end">
                <button
                  onClick={handleCancel}
                  className="px-4 py-2 bg-gray-300 rounded mr-2"
                >
                  Cancelar
                </button>
                <button
                  onClick={handleSave}
                  className="px-4 py-2 bg-blue-500 text-white rounded"
                >
                  Guardar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default PlaceTable;
