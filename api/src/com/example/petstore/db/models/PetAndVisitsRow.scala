package com.example.petstore.db.models

import ba.sake.squery.read.SqlReadRow

final case class PetAndVisitsRow(
    p: PetsRow,
    t: TypesRow,
    v: Option[VisitsRow]
) derives SqlReadRow
