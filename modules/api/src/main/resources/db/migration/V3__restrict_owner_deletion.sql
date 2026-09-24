ALTER TABLE public.pets DROP CONSTRAINT pets_owner_id_fkey;

ALTER TABLE public.pets
  ADD CONSTRAINT pets_owner_id_fkey
  FOREIGN KEY (owner_id) REFERENCES public.owners(id) ON DELETE RESTRICT;
