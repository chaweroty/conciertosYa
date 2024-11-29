import axios from "axios";
import React, { useEffect, useState } from "react";

const API_URL = "http://localhost:8080/artists";

const ArtistTable = () => {
  const [artists, setArtists] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [modalData, setModalData] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [selectedArtist, setSelectedArtist] = useState(null);

  const token = localStorage.getItem("token");

  useEffect(() => {
    fetchArtists();
  }, []);

  const fetchArtists = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`${API_URL}/get-all`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setArtists(response.data.ourArtistsList || []);
    } catch (err) {
      setError(
        "Error al obtener los artistas. Verifica tu conexión o permisos."
      );
      console.error("Error fetching artists:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = () => {
    setIsEditing(false);
    setModalData({
      name: "",
      musicalGenre: "",
      instagram: "",
      facebook: "",
      contact: "",
    });
    setShowModal(true);
  };

  const handleEdit = (artist) => {
    setIsEditing(true);
    setModalData(artist);
    setShowModal(true);
  };

  const handleDelete = async () => {
    try {
      await axios.delete(`${API_URL}/delete/${selectedArtist.id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setArtists(artists.filter((artist) => artist.id !== selectedArtist.id));
      setIsDeleteModalOpen(false);
    } catch (err) {
      setError("Error al eliminar el artista.");
    }
  };

  const openDeleteModal = (artist) => {
    setSelectedArtist(artist);
    setIsDeleteModalOpen(true);
  };

  const validateData = () => {
    const { name, musicalGenre, instagram, facebook, contact } = modalData;
    if (!name || !musicalGenre || !instagram || !facebook || !contact) {
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
      fetchArtists();
    } catch (err) {
      setError("Error al guardar el artista.");
      console.error("Error saving artist:", err);
    }
  };

  const handleCancel = () => {
    setShowModal(false);
    setModalData(null);
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Artistas</h1>

      {loading && <p>Cargando artistas...</p>}
      {error && <p className="text-red-500">{error}</p>}

      {!loading && !error && (
        <div>
          <button
            onClick={handleCreate}
            className="mb-4 p-2 bg-blue-500 text-white rounded"
          >
            Crear Artista
          </button>
          <table className="table-auto w-full border-collapse border border-gray-300">
            <thead>
              <tr className="bg-gray-200">
                <th className="border border-gray-300 px-4 py-2">Nombre</th>
                <th className="border border-gray-300 px-4 py-2">Género</th>
                <th className="border border-gray-300 px-4 py-2">Contacto</th>
                <th className="border border-gray-300 px-4 py-2">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {artists.map((artist) => (
                <tr key={artist.id} className="hover:bg-gray-100">
                  <td className="border border-gray-300 px-4 py-2">
                    {artist.name}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {artist.musicalGenre}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {artist.contact}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    <button
                      onClick={() => handleEdit(artist)}
                      className="px-4 py-2 bg-yellow-500 text-white rounded mr-2"
                    >
                      Editar
                    </button>
                    <button
                      onClick={() => openDeleteModal(artist)}
                      className="px-4 py-2 bg-red-600 text-white rounded"
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

      {isDeleteModalOpen && selectedArtist && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white rounded-lg shadow p-6 w-1/3 text-center">
            <h2 className="text-lg font-bold mb-4">Confirmar Eliminación</h2>
            <p className="mb-4">
              ¿Estás seguro de que quieres eliminar <b>{selectedArtist.name}</b>?
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
          <div className="bg-white p-6 rounded-lg shadow-lg w-1/3">
            <h2 className="text-lg font-bold mb-4">
              {isEditing ? "Editar Artista" : "Crear Artista"}
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
                <label className="block text-sm font-semibold">Género</label>
                <input
                  type="text"
                  value={modalData.musicalGenre}
                  onChange={(e) =>
                    setModalData({ ...modalData, musicalGenre: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-semibold">instagram</label>
                <textarea
                  value={modalData.bio}
                  onChange={(e) =>
                    setModalData({ ...modalData, bio: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-semibold">facebook</label>
                <input
                  type="text"
                  value={modalData.facebook}
                  onChange={(e) =>
                    setModalData({ ...modalData, facebook: e.target.value })
                  }
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-semibold">Contacto</label>
                <input
                  type="text"
                  value={modalData.contact}
                  onChange={(e) =>
                    setModalData({ ...modalData, contact: e.target.value })
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

export default ArtistTable;
